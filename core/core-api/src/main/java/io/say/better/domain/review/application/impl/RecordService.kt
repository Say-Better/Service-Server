package io.say.better.domain.review.application.impl;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.RecordReadRepository;
import io.say.better.storage.mysql.dao.repository.RecordWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

	private final RecordReadRepository recordReadRepository;
	private final RecordWriteRepository recordWriteRepository;

}
