package com.saybetter.domain.member.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saybetter.global.common.response.JwtTokenResponseDto;
import com.saybetter.global.common.response.ResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Oauth", description = "Oauth API")
@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class OauthController {

	@GetMapping("/login")
	public ResponseDto<JwtTokenResponseDto> googleOauthLogin(
			@RequestParam String code
	) {
		return null;
	}
}
