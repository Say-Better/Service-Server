package com.saybetter.global.utils;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.saybetter.global.config.properties.JwtProperties;

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
public class JwtUtil {

	@Getter
	private final JwtProperties jwtProperties;
	private final RedisUtil redisUtil;

	/**
	 * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
	 * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
	 */
	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String EMAIL_CLAIM = "email";
	private static final String BEARER = "Bearer ";

	/**
	 * AccessToken 생성 메소드
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
	 * RefreshToken 생성
	 * RefreshToken은 Claim에 email도 넣지 않으므로 withClaim() X
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
	 * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
	 * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
	 */
	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(this.jwtProperties.refreshHeader()))
				.filter(refreshToken -> refreshToken.startsWith(BEARER))
				.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	/**
	 * 헤더에서 AccessToken 추출
	 * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
	 * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
	 */
	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(this.jwtProperties.accessHeader()))
				.filter(refreshToken -> refreshToken.startsWith(BEARER))
				.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	/**
	 * AccessToken에서 Email 추출
	 * 추출 전에 JWT.require()로 검증기 생성
	 * verify로 AceessToken 검증 후
	 * 유효하다면 getClaim()으로 이메일 추출
	 * 유효하지 않다면 빈 Optional 객체 반환
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
	 */
	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(this.jwtProperties.accessHeader(), accessToken);
	}

	/**
	 * RefreshToken 헤더 설정
	 */
	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(this.jwtProperties.refreshHeader(), refreshToken);
	}

	/**
	 * RefreshToken DB 저장(업데이트)
	 */
	public void updateRefreshToken(String email, String refreshToken) {
		redisUtil.setDataExpire(email, refreshToken, this.jwtProperties.refreshExpiration());
	}

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
