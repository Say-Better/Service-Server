package com.saybetter.global.auth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.saybetter.global.auth.CustomOAuth2User;
import com.saybetter.global.jwt.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtUtils;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) {
		log.info("OAuth2 Login 성공!");
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
	}

	private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) {
		String accessToken = jwtUtils.createAccessToken(oAuth2User.getEmail());
		String refreshToken = jwtUtils.createRefreshToken();
		response.addHeader(jwtUtils.getJwtProperties().accessHeader(), "Bearer " + accessToken);
		response.addHeader(jwtUtils.getJwtProperties().refreshHeader(), "Bearer " + refreshToken);

		jwtUtils.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		jwtUtils.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
	}

}
