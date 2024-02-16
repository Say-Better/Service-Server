package com.saybetter.domain.review.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.review.dao.repository.RecordReadRepository;
import com.saybetter.domain.review.dao.repository.RecordWriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

	private final RecordReadRepository recordReadRepository;
	private final RecordWriteRepository recordWriteRepository;

}
