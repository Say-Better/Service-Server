package io.say.better.global.jwt.service

import io.jsonwebtoken.*
import io.say.better.global.config.logger.logger
import io.say.better.global.config.properties.JwtProperties
import io.say.better.storage.redis.RedisUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import java.util.*

@Slf4j
@Service
@RequiredArgsConstructor
class JwtService(
    val jwtProperties: JwtProperties,
    private val redisUtil: RedisUtil
) {

    private val log = logger()

    /**
     * AccessToken 생성 메소드
     *
     * @param email AccessToken에 담을 email
     * @return AccessToken
     */
    fun createAccessToken(email: String): String {
        val claims = Jwts.claims(mapOf<String, Any>(EMAIL_CLAIM to email))
        val now = Date()
        return Jwts.builder() // JWT 토큰을 생성하는 빌더 반환
            .setClaims(claims) // JWT의 Subject 지정 -> AccessToken이므로 AccessToken
            .setSubject(ACCESS_TOKEN_SUBJECT)
            .setIssuedAt(now) // 토큰 발급 시간 설정
            .setExpiration(Date(now.time + jwtProperties!!.accessExpiration)) // 토큰 만료 시간 설정
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secret) // 지정한 secret 키로 암호화
            .compact()
    }

    /**
     * RefreshToken 생성 메소드
     *
     * @return RefreshToken
     */
    fun createRefreshToken(): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(REFRESH_TOKEN_SUBJECT)
            .setExpiration(Date(now.time + jwtProperties!!.refreshExpiration))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
            .compact()
    }

    /**
     * AccessToken 헤더에 실어서 보내기
     *
     * @param response    HttpServletResponse
     * @param accessToken AccessToken
     */
    fun sendAccessToken(
        response: HttpServletResponse,
        accessToken: String?
    ) {
        response.status = HttpServletResponse.SC_OK
        response.setHeader(jwtProperties!!.accessHeader, accessToken)
        log.info("재발급된 Access Token : {}", accessToken)
    }

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     *
     * @param response     HttpServletResponse
     * @param accessToken  AccessToken
     * @param refreshToken RefreshToken
     */
    fun sendAccessAndRefreshToken(
        response: HttpServletResponse,
        accessToken: String?, refreshToken: String?
    ) {
        response.status = HttpServletResponse.SC_OK

        setAccessTokenHeader(response, accessToken)
        setRefreshTokenHeader(response, refreshToken)
        log.info("Access Token, Refresh Token 헤더 설정 완료")
    }

    /**
     * 헤더에서 RefreshToken 추출
     *
     *
     * 1. HttpServletRequest에서 RefeshHeader 추출 <br></br>
     * 2. Optional로 반환
     *
     * @param request HttpServletRequest
     * @return Optional<String> RefreshToken
    </String> */
    fun extractRefreshToken(request: HttpServletRequest): Optional<String> {
        return Optional.ofNullable(request.getHeader(jwtProperties!!.refreshHeader))
            .filter { refreshToken: String -> refreshToken.startsWith(BEARER) }
            .map { refreshToken: String -> refreshToken.replace(BEARER, "") }
    }

    /**
     * 헤더에서 AccessToken 추출
     *
     *
     * 1. HttpServletRequest에서 AccessHeader 추출 <br></br>
     * 2. Optional로 반환
     *
     * @param request HttpServletRequest
     * @return Optional<String> AccessToken
    </String> */
    fun extractAccessToken(request: HttpServletRequest): Optional<String> {
        return Optional.ofNullable(request.getHeader(jwtProperties!!.accessHeader))
            .filter { accessToken: String -> accessToken.startsWith(BEARER) }
            .map { accessToken: String -> accessToken.replace(BEARER, "") }
    }

    /**
     * AccessToken에서 Email 추출
     *
     *
     * 1. AccessToken을 파싱하여 <br></br>
     * 2. email claim을 추출하여 <br></br>
     * 3. Optional로 반환
     *
     * @param accessToken AccessToken
     * @return Optional<String> Email
    </String> */
    fun extractEmail(accessToken: String?): Optional<String> {
        return try {
            Optional.ofNullable(
                Jwts.parser()
                    .setSigningKey(jwtProperties!!.secret)
                    .parseClaimsJws(accessToken)
                    .body
                    .get(EMAIL_CLAIM, String::class.java)
            )
        } catch (e: Exception) {
            Optional.empty()
        }
    }

    /**
     * AccessToken 헤더 설정
     *
     * @param response    HttpServletResponse
     * @param accessToken AccessToken
     */
    fun setAccessTokenHeader(response: HttpServletResponse, accessToken: String?) {
        response.setHeader(jwtProperties!!.accessHeader, accessToken)
    }

    /**
     * RefreshToken 헤더 설정
     *
     * @param response     HttpServletResponse
     * @param refreshToken RefreshToken
     */
    fun setRefreshTokenHeader(response: HttpServletResponse, refreshToken: String?) {
        response.setHeader(jwtProperties!!.refreshHeader, refreshToken)
    }

    /**
     * RefreshToken DB 저장(업데이트)
     *
     * @param email Email
     */
    fun updateRefreshToken(email: String?, refreshToken: String?) {
        redisUtil!!.setDataExpire(refreshToken, email, jwtProperties!!.refreshExpiration)
    }

    /**
     * token 유효성 검사
     *
     *
     * 1. 만료된 토큰인지 확인 <br></br>
     * 2. 지원되는 토큰인지 확인 <br></br>
     * 3. 토큰에 오류가 있는지 확인
     *
     * @param token JWT 토큰
     * @return 유효한 토큰인지 여부
     */
    fun isTokenValid(token: String?): Boolean {
        try {
            val claimsJws = Jwts.parser().setSigningKey(jwtProperties!!.secret).parseClaimsJws(token)
            return !claimsJws.body.expiration.before(Date())
        } catch (exception: ExpiredJwtException) {
            log.warn("만료된 jwt 입니다.")
        } catch (exception: UnsupportedJwtException) {
            log.warn("지원되지 않는 jwt 입니다.")
        } catch (exception: IllegalArgumentException) {
            log.warn("jwt 에 오류가 존재합니다.")
        }
        return false
    }

    companion object {
        private const val ACCESS_TOKEN_SUBJECT = "AccessToken"
        private const val REFRESH_TOKEN_SUBJECT = "RefreshToken"
        private const val EMAIL_CLAIM = "email"
        private const val BEARER = "Bearer "
    }
}
