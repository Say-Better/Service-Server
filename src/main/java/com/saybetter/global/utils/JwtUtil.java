package com.saybetter.global.utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saybetter.domain.member.domain.Member;
import com.saybetter.global.common.response.JwtTokenResponseDto;
import com.saybetter.global.config.properties.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {
	private final JwtProperties jwtProperties;
	private final RedisUtil redisUtil;

	// HttpServletRequest 부터 Access Token 추출
	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(this.jwtProperties.accessHeader()))
				.filter(StringUtils::hasText)
				.filter(accessToken -> accessToken.startsWith(jwtProperties.bearer()))
				.map(accessToken -> accessToken.replace(jwtProperties.bearer(), ""));
	}

	// HttpServletRequest 부터 Refresh Token 추출
	public String extractRefreshToken(HttpServletRequest request) {
		return request.getHeader(this.jwtProperties.refreshHeader());
	}

	// access token 생성
	public String createAccessToken(String payload) {
		return this.createToken(payload, this.jwtProperties.accessExpiration());
	}

	// refresh token 생성
	public String createRefreshToken() {
		return this.createToken(UUID.randomUUID().toString(), this.jwtProperties.refreshExpiration());

	}

	// access token 으로부터 회원 아이디 추출
	public String getUserIdFromToken(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(this.jwtProperties.secret())
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
		} catch (Exception exception) {
			throw new JwtException("Access Token is not valid");
		}
	}

	// oauth 로그인 & 일반 로그인 시 jwt 응답 생성 + redis refresh 저장
	public JwtTokenResponseDto createServiceToken(Member member) {
		String accessToken = this.createAccessToken(String.valueOf(member.getMemberId()));
		String refreshToken = this.createRefreshToken();

		/* 서비스 토큰 생성 */
		JwtTokenResponseDto jwtTokenResponseDto = JwtTokenResponseDto.builder()
				.accessToken(this.jwtProperties.bearer() + " " + accessToken)
				.refreshToken(refreshToken)
				.expiredTime(LocalDateTime.now().plusSeconds(this.jwtProperties.accessExpiration() / 1000))
				.isExisted(member.getLoginId() != null)
				.build();

		/* redis refresh token 저장 */
		this.redisUtil.setDataExpire(
				String.valueOf(member.getMemberId()),
				jwtTokenResponseDto.getRefreshToken(),
				this.jwtProperties.refreshExpiration());

		return jwtTokenResponseDto;
	}

	// token 유효성 검증
	public boolean validateToken(String token) {
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

	// 실제 token 생성 로직
	private String createToken(String payload, Long tokenExpiration) {
		Claims claims = Jwts.claims().setSubject(payload);
		Date tokenExpiresIn = new Date(new Date().getTime() + tokenExpiration);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(tokenExpiresIn)
				.signWith(SignatureAlgorithm.HS512, this.jwtProperties.secret())
				.compact();
	}
}
