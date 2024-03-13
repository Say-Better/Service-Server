package io.say.better.global.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class CodeUtil {

	public String createConnectCode() {
		int connectCodeLength = 6;
		boolean useLetters = true;
		boolean useNumbers = true;

		return RandomStringUtils.random(connectCodeLength, useLetters, useNumbers);
	}
}
