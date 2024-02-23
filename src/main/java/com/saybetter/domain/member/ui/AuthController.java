package com.saybetter.domain.member.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saybetter.domain.member.application.AuthFacade;
import com.saybetter.global.common.constant.RoleType;
import com.saybetter.global.common.response.ResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthFacade authFacade;

	@PostMapping("/assign/educator")
	public ResponseDto<Void> assignEducator() {
		RoleType role = RoleType.EDUCATOR;
		authFacade.assignUserRole(role);
		return ResponseDto.onSuccess(null);
	}

	@PostMapping("/assign/learner")
	public ResponseDto<Void> assignLearner() {
		RoleType role = RoleType.LEARNER;
		authFacade.assignUserRole(role);
		return ResponseDto.onSuccess(null);
	}
}
