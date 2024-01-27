package com.aesp.domain.temp.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aesp.domain.temp.application.TempService;
import com.aesp.domain.temp.converter.TempResponseConverter;
import com.aesp.domain.temp.dto.TempResponse;
import com.aesp.global.common.response.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Temp", description = "Swagger Test API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/temp")
public class TempController {

	private final TempService tempService;

	@Operation(summary = "Running Test API", description = "서비스 동작 확인을 위한 test api")
	@GetMapping("")
	public ResponseDto<String> test() {
		return ResponseDto.onSuccess("Service is running");
	}

	@Operation(summary = "Exception Test", description = "Exception 발생 확인을 위한 test api")
	@Parameter(name = "flag", description = "1을 입력하면 예외가 발생됩니다.")
	@GetMapping("/exception")
	public ResponseDto<TempResponse.ExceptionTestDto> exception(@RequestParam("flag") int flag) {
		String message = tempService.exception(flag);
		return ResponseDto.onSuccess(TempResponseConverter.toExceptionTestDto(message));
	}

}
