package com.saybetter.domain.review.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.review.dao.repository.RecordSymbolReadRepository;
import com.saybetter.domain.review.dao.repository.RecordSymbolWriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordSymbolService {

	private final RecordSymbolReadRepository recordSymbolReadRepository;
	private final RecordSymbolWriteRepository recordSymbolWriteRepository;

}
