package io.say.better.domain.member.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.say.better.domain.member.application.MemberFacade;
import io.say.better.global.common.response.ResponseDto;
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
		String code = memberFacade.createConnectCode();
		return ResponseDto.onSuccess(code);
	}

	@PostMapping("/connect/{code}")
	public ResponseDto<Void> connect(
			@PathVariable String code
	) {
		memberFacade.connect(code);
		return ResponseDto.onSuccess(null);
	}

}
