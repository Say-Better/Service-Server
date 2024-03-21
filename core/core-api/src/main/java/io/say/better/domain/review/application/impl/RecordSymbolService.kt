package io.say.better.domain.review.application.impl;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.RecordSymbolReadRepository;
import io.say.better.storage.mysql.dao.repository.RecordSymbolWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordSymbolService {

	private final RecordSymbolReadRepository recordSymbolReadRepository;
	private final RecordSymbolWriteRepository recordSymbolWriteRepository;

}
