package com.saybetter.global.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.saybetter.domain.member.dao.repository.MemberReadRepository;
import com.saybetter.domain.member.domain.Member;
import com.saybetter.global.config.properties.JwtProperties;
import com.saybetter.global.jwt.service.JwtService;
import com.saybetter.global.utils.RedisUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JwtAuthenticationProcessingFilter
 * <p>
 * "/login" 이외의 모든 요청에 대해 Jwt 토큰을 검증하는 필터
 * <p>
 * 1. RefreshToken이 없고, AccessToken이 유효한 경우 <br>
 * - 인증 성공 처리, RefreshToken을 재발급하지는 않는다. <br>
 * 2. RefreshToken이 없고, AccessToken이 없거나 유효하지 않은 경우  <br>
 * - 인증 실패 처리, 403 ERROR <br>
 * 3. RefreshToken이 있는 경우 <br>
 * - DB의 RefreshToken과 비교하여 일치하면 AccessToken 재발급, RefreshToken 재발급 <br>
 * - 인증 성공 처리는 하지 않고 실패 처리 및 403 ERROR <br>
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

	private static final String NO_CHECK_URI = "/login";

	private final MemberReadRepository memberReadRepository;

	private final JwtProperties jwtProperties;
	private final JwtService jwtService;
	private final RedisUtil redisUtil;
	private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {
		if (isNoCheckUri(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		// RefreshToken 추출, AccessToken이 만료되지 않은 이상 RefreshToken은 null
		String refreshToken = jwtService.extractRefreshToken(request)
				.filter(jwtService::isTokenValid)
				.orElse(null);

		// 리프레시 토큰이 요청 헤더에 존재했다면, 사용자가 AccessToken이 만료되어서
		// RefreshToken까지 보낸 것이므로 리프레시 토큰이 DB의 리프레시 토큰과 일치하는지 판단 후,
		// 일치한다면 AccessToken을 재발급해준다.
		if (refreshToken != null) {
			checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
			return;
		}

		// RefreshToken이 없거나 유효하지 않다면, AccessToken을 검사하고 인증을 처리하는 로직 수행
		// AccessToken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 403 에러 발생
		// AccessToken이 유효하다면, 인증 객체가 담긴 상태로 다음 필터로 넘어가기 때문에 인증 성공
		if (refreshToken == null) {
			checkAccessTokenAndAuthentication(request, response, filterChain);
		}

	}

	/**
	 * 요청 URI가 "/login"인지 확인
	 *
	 * @param request HttpServletRequest
	 * @return "/login"인 경우 true
	 */
	private boolean isNoCheckUri(HttpServletRequest request) {
		return request.getRequestURI().equals(NO_CHECK_URI);
	}

	/**
	 * RefreshToken이 있는 경우, DB의 RefreshToken과 비교하여 일치하면 AccessToken 및 RefreshToken 재발급
	 *
	 * @param response     HttpServletResponse
	 * @param refreshToken
	 */
	private void checkRefreshTokenAndReIssueAccessToken(
			HttpServletResponse response,
			String refreshToken
	) {
		log.info("JwtAuthenticationProcessingFilter.checkRefreshTokenAndReIssueAccessToken() 실행 - RefreshToken 검증");
		String email = redisUtil.getData(refreshToken);
		memberReadRepository.findByEmail(email)
				.ifPresent(user -> {
					String reIssuedRefreshToken = reIssueRefreshToken(user);
					jwtService.sendAccessAndRefreshToken(
							response,
							jwtService.createAccessToken(user.getEmail()),
							reIssuedRefreshToken);
				});
	}

	/**
	 * RefreshToken 재발급
	 *
	 * @param user Member
	 * @return 재발급된 RefreshToken
	 */
	private String reIssueRefreshToken(Member user) {
		String reIssuedRefreshToken = jwtService.createRefreshToken();
		redisUtil.setDataExpire(reIssuedRefreshToken, user.getEmail(), this.jwtProperties.refreshExpiration());
		return reIssuedRefreshToken;
	}

	/**
	 * AccessToken 검증 및 인증 처리
	 * <p>
	 * 1. AccessToken 추출 <br>
	 * 2. AccessToken 유효성 검증 <br>
	 * 3. AccessToken에서 email 추출 <br>
	 * 4. email로 Member 조회 <br>
	 * 5. Member가 존재하면 인증 객체 저장 <br>
	 * 6. 다음 필터로 넘어가기
	 *
	 * @param request     HttpServletRequest
	 * @param response    HttpServletResponse
	 * @param filterChain FilterChain
	 */
	private void checkAccessTokenAndAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {
		log.info("JwtAuthenticationProcessingFilter.checkAccessTokenAndAuthentication() 실행 - AccessToken 검증");
		jwtService.extractAccessToken(request)
				.filter(jwtService::isTokenValid)
				.ifPresent(accessToken -> jwtService.extractEmail(accessToken)
						.ifPresent(email ->
								memberReadRepository.findByEmail(email).ifPresent(this::saveAuthentication)
						));

		filterChain.doFilter(request, response);
	}

	/**
	 * 인증 허가
	 *
	 * @param member Member
	 */
	private void saveAuthentication(Member member) {
		log.info("JwtAuthenticationProcessingFilter.saveAuthentication() 실행 - 인증 객체 저장");

		UserDetails userDetails = User.builder()
				.username(member.getEmail())
				.password(member.getLoginId())
				.roles(member.getRole().name())
				.build();

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null,
				authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
