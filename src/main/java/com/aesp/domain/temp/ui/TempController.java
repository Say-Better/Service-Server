package com.aesp.domain.temp.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aesp.domain.temp.application.TempService;
import com.aesp.domain.temp.converter.TempResponseConverter;
import com.aesp.domain.temp.dto.TempResponse;
import com.aesp.global.common.response.ResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/temp")
public class TempController {

	private final TempService tempService;

	@GetMapping("")
	public ResponseDto<String> test() {
		return ResponseDto.onSuccess("Service is running");
	}

	@GetMapping("/exception")
	public ResponseDto<TempResponse.ExceptionTestDto> exception(@RequestParam("flag") int flag) {
		String message = tempService.exception(flag);
		return ResponseDto.onSuccess(TempResponseConverter.toExceptionTestDto(message));
	}

}
