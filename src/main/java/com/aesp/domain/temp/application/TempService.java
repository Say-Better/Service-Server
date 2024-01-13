package com.aesp.domain.temp.application;

import org.springframework.stereotype.Service;

import com.aesp.domain.temp.exception.TempException;
import com.aesp.global.common.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TempService {

	public String exception(int flag) {
		if (flag == 1) {
			throw new TempException(ErrorStatus.TEMP_ERROR);
		}
		return "1을 입력하면 예외가 발생됩니다.";
	}
}
