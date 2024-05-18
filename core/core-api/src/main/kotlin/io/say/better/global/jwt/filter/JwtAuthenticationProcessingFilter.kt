package io.say.better.global.jwt.filter

import io.say.better.global.config.logger.logger
import io.say.better.global.config.properties.JwtProperties
import io.say.better.global.jwt.service.JwtService
import io.say.better.storage.mysql.dao.repository.EducatorReadRepository
import io.say.better.storage.mysql.dao.repository.LearnerReadRepository
import io.say.better.storage.mysql.domain.entity.Member
import io.say.better.storage.redis.RedisUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

/**
 * JwtAuthenticationProcessingFilter
 *
 *
 * "/login" 이외의 모든 요청에 대해 Jwt 토큰을 검증하는 필터
 *
 *
 * 1. RefreshToken이 없고, AccessToken이 유효한 경우 <br></br>
 * - 인증 성공 처리, RefreshToken을 재발급하지는 않는다. <br></br>
 * 2. RefreshToken이 없고, AccessToken이 없거나 유효하지 않은 경우  <br></br>
 * - 인증 실패 처리, 403 ERROR <br></br>
 * 3. RefreshToken이 있는 경우 <br></br>
 * - DB의 RefreshToken과 비교하여 일치하면 AccessToken 재발급, RefreshToken 재발급 <br></br>
 * - 인증 성공 처리는 하지 않고 실패 처리 및 403 ERROR <br></br>
 */
class JwtAuthenticationProcessingFilter(
    private val educatorReadRepository: EducatorReadRepository,
    private val learnerReadRepository: LearnerReadRepository,
    private val jwtProperties: JwtProperties,
    private val jwtService: JwtService,
    private val redisUtil: RedisUtil,
    private val authoritiesMapper: GrantedAuthoritiesMapper = NullAuthoritiesMapper()
) : OncePerRequestFilter() {

    private val log = logger()

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (isNoCheckUri(request)) {
            filterChain.doFilter(request, response)
            return
        }

        // RefreshToken 추출, AccessToken이 만료되지 않은 이상 RefreshToken은 null
        val refreshToken = jwtService.extractRefreshToken(request)
            .filter { token: String? -> jwtService.isTokenValid(token) }
            .orElse(null)

        if (refreshToken != null) {
            /*
			 리프레시 토큰이 요청 헤더에 존재했다면, 사용자가 AccessToken이 만료되어서
			 RefreshToken까지 보낸 것이므로 리프레시 토큰이 DB의 리프레시 토큰과 일치하는지 판단 후,
			 일치한다면 AccessToken을 재발급해준다.
			*/
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken)
        } else {
            /*
			 RefreshToken이 없거나 유효하지 않다면, AccessToken을 검사하고 인증을 처리하는 로직 수행
			 AccessToken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 403 에러 발생
			 AccessToken이 유효하다면, 인증 객체가 담긴 상태로 다음 필터로 넘어가기 때문에 인증 성공
			*/
            checkAccessTokenAndAuthentication(request, response, filterChain)
        }
    }

    /**
     * 요청 URI가 "/login"인지 확인
     *
     * @param request HttpServletRequest
     * @return "/login"인 경우 true
     */
    private fun isNoCheckUri(request: HttpServletRequest): Boolean {
        return request.requestURI == NO_CHECK_URI
    }

    /**
     * RefreshToken이 있는 경우, DB의 RefreshToken과 비교하여 일치하면 AccessToken 및 RefreshToken 재발급
     *
     * @param response     HttpServletResponse
     * @param refreshToken
     */
    private fun checkRefreshTokenAndReIssueAccessToken(
        response: HttpServletResponse,
        refreshToken: String
    ) {
        log.info("JwtAuthenticationProcessingFilter.checkRefreshTokenAndReIssueAccessToken() 실행 - RefreshToken 검증")
        val email = redisUtil.getData(refreshToken)
        educatorReadRepository.findByEmail(email)
            .ifPresent { user: Member ->
                val reIssuedRefreshToken = reIssueRefreshToken(user)
                jwtService.sendAccessAndRefreshToken(
                    response,
                    jwtService.createAccessToken(user.email),
                    reIssuedRefreshToken
                )
            }

        learnerReadRepository.findByEmail(email)
            .ifPresent { user: Member ->
                val reIssuedRefreshToken = reIssueRefreshToken(user)
                jwtService.sendAccessAndRefreshToken(
                    response,
                    jwtService.createAccessToken(user.email),
                    reIssuedRefreshToken
                )
            }
    }

    /**
     * RefreshToken 재발급
     *
     * @param user Member
     * @return 재발급된 RefreshToken
     */
    private fun reIssueRefreshToken(user: Member): String? {
        val reIssuedRefreshToken = jwtService.createRefreshToken()
        redisUtil.setDataExpire(reIssuedRefreshToken, user.email, jwtProperties.refreshExpiration)
        return reIssuedRefreshToken
    }

    /**
     * AccessToken 검증 및 인증 처리
     *
     *
     * 1. AccessToken 추출 <br></br>
     * 2. AccessToken 유효성 검증 <br></br>
     * 3. AccessToken에서 email 추출 <br></br>
     * 4. email로 Member 조회 <br></br>
     * 5. Member가 존재하면 인증 객체 저장 <br></br>
     * 6. 다음 필터로 넘어가기
     *
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param filterChain FilterChain
     */
    @Throws(ServletException::class, IOException::class)
    private fun checkAccessTokenAndAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        log.info("JwtAuthenticationProcessingFilter.checkAccessTokenAndAuthentication() 실행 - AccessToken 검증")
        jwtService.extractAccessToken(request)
            .filter { token: String? -> jwtService.isTokenValid(token) }
            .ifPresent { accessToken: String? ->
                jwtService.extractEmail(accessToken).ifPresent { email: String? ->
                    run {
                        val educatorOptional = educatorReadRepository.findByEmail(email)
                        if (educatorOptional.isPresent) {
                            this.saveAuthentication(educatorOptional.get())
                            return@run
                        }

                        learnerReadRepository
                            .findByEmail(email)
                            .ifPresent { member: Member -> this.saveAuthentication(member) }
                    }
                }
            }

        filterChain.doFilter(request, response)
    }

    /**
     * 인증 허가
     *
     * @param member Member
     */
    private fun saveAuthentication(member: Member) {
        log.info("JwtAuthenticationProcessingFilter.saveAuthentication() 실행 - 인증 객체 저장")

        val userDetails = User.builder()
            .username(member.email)
            .password(member.loginId)
            .roles(member.role.name)
            .build()

        val authentication: Authentication = UsernamePasswordAuthenticationToken(
            userDetails, null,
            authoritiesMapper.mapAuthorities(userDetails.authorities)
        )

        SecurityContextHolder.getContext().authentication = authentication
    }

    companion object {
        private const val NO_CHECK_URI = "/login"
    }
}
