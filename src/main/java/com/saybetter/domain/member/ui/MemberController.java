package com.saybetter.domain.member.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saybetter.domain.member.application.MemberFacade;
import com.saybetter.global.common.response.ResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Member", description = "Member API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberFacade memberFacade;

	@GetMapping("/connect/code")
	public ResponseDto<String> getConnectCode() {
		memberFacade.createConnectCode();
		return ResponseDto.onSuccess(memberFacade.getConnectCode());
	}
}
