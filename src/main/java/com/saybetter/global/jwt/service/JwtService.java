package com.saybetter.global.jwt.service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.saybetter.global.config.properties.JwtProperties;
import com.saybetter.global.utils.RedisUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

	@Getter
	private final JwtProperties jwtProperties;
	private final RedisUtil redisUtil;

	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String EMAIL_CLAIM = "email";
	private static final String BEARER = "Bearer ";

	/**
	 * AccessToken 생성 메소드
	 *
	 * @param email AccessToken에 담을 email
	 * @return AccessToken
	 */
	public String createAccessToken(String email) {
		Claims claims = Jwts.claims(Map.of(EMAIL_CLAIM, email));
		Date now = new Date();
		return Jwts.builder() // JWT 토큰을 생성하는 빌더 반환
				.setClaims(claims) // JWT의 Subject 지정 -> AccessToken이므로 AccessToken
				.setSubject(ACCESS_TOKEN_SUBJECT)
				.setIssuedAt(now) // 토큰 발급 시간 설정
				.setExpiration(new Date(now.getTime() + this.jwtProperties.accessExpiration())) // 토큰 만료 시간 설정
				.signWith(SignatureAlgorithm.HS512, this.jwtProperties.secret()) // 지정한 secret 키로 암호화
				.compact();
	}

	/**
	 * RefreshToken 생성 메소드
	 *
	 * @return RefreshToken
	 */
	public String createRefreshToken() {
		Date now = new Date();
		return Jwts.builder()
				.setSubject(REFRESH_TOKEN_SUBJECT)
				.setExpiration(new Date(now.getTime() + this.jwtProperties.refreshExpiration()))
				.signWith(SignatureAlgorithm.HS512, this.jwtProperties.secret())
				.compact();
	}

	/**
	 * AccessToken 헤더에 실어서 보내기
	 *
	 * @param response    HttpServletResponse
	 * @param accessToken AccessToken
	 */
	public void sendAccessToken(
			HttpServletResponse response,
			String accessToken
	) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader(this.jwtProperties.accessHeader(), accessToken);
		log.info("재발급된 Access Token : {}", accessToken);
	}

	/**
	 * AccessToken + RefreshToken 헤더에 실어서 보내기
	 *
	 * @param response     HttpServletResponse
	 * @param accessToken  AccessToken
	 * @param refreshToken RefreshToken
	 */
	public void sendAccessAndRefreshToken(
			HttpServletResponse response,
			String accessToken, String refreshToken
	) {
		response.setStatus(HttpServletResponse.SC_OK);

		setAccessTokenHeader(response, accessToken);
		setRefreshTokenHeader(response, refreshToken);
		log.info("Access Token, Refresh Token 헤더 설정 완료");
	}

	/**
	 * 헤더에서 RefreshToken 추출
	 * <p>
	 * 1. HttpServletRequest에서 RefeshHeader 추출 <br>
	 * 2. Optional로 반환
	 *
	 * @param request HttpServletRequest
	 * @return Optional<String> RefreshToken
	 */
	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(this.jwtProperties.refreshHeader()))
				.filter(refreshToken -> refreshToken.startsWith(BEARER))
				.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	/**
	 * 헤더에서 AccessToken 추출
	 * <p>
	 * 1. HttpServletRequest에서 AccessHeader 추출 <br>
	 * 2. Optional로 반환
	 *
	 * @param request HttpServletRequest
	 * @return Optional<String> AccessToken
	 */
	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(this.jwtProperties.accessHeader()))
				.filter(accessToken -> accessToken.startsWith(BEARER))
				.map(accessToken -> accessToken.replace(BEARER, ""));
	}

	/**
	 * AccessToken에서 Email 추출
	 * <p>
	 * 1. AccessToken을 파싱하여 <br>
	 * 2. email claim을 추출하여 <br>
	 * 3. Optional로 반환
	 *
	 * @param accessToken AccessToken
	 * @return Optional<String> Email
	 */
	public Optional<String> extractEmail(String accessToken) {
		try {
			return Optional.ofNullable(Jwts.parser()
					.setSigningKey(this.jwtProperties.secret())
					.parseClaimsJws(accessToken)
					.getBody()
					.get(EMAIL_CLAIM, String.class));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	/**
	 * AccessToken 헤더 설정
	 *
	 * @param response    HttpServletResponse
	 * @param accessToken AccessToken
	 */
	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(this.jwtProperties.accessHeader(), accessToken);
	}

	/**
	 * RefreshToken 헤더 설정
	 *
	 * @param response     HttpServletResponse
	 * @param refreshToken RefreshToken
	 */
	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(this.jwtProperties.refreshHeader(), refreshToken);
	}

	/**
	 * RefreshToken DB 저장(업데이트)
	 *
	 * @param email Email
	 */
	public void updateRefreshToken(String email, String refreshToken) {
		redisUtil.setDataExpire(refreshToken, email, this.jwtProperties.refreshExpiration());
	}

	/**
	 * token 유효성 검사
	 * <p>
	 * 1. 만료된 토큰인지 확인 <br>
	 * 2. 지원되는 토큰인지 확인 <br>
	 * 3. 토큰에 오류가 있는지 확인
	 *
	 * @param token JWT 토큰
	 * @return 유효한 토큰인지 여부
	 */
	public boolean isTokenValid(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.jwtProperties.secret()).parseClaimsJws(token);
			return !claimsJws.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException exception) {
			log.warn("만료된 jwt 입니다.");
		} catch (UnsupportedJwtException exception) {
			log.warn("지원되지 않는 jwt 입니다.");
		} catch (IllegalArgumentException exception) {
			log.warn("jwt 에 오류가 존재합니다.");
		}
		return false;
	}
}
