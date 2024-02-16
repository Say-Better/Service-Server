package com.saybetter.domain.review.application.impl;

import org.springframework.stereotype.Service;

import com.saybetter.domain.review.dao.repository.ReviewReadRepository;
import com.saybetter.domain.review.dao.repository.ReviewWriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewReadRepository reviewReadRepository;
	private final ReviewWriteRepository reviewWriteRepository;

}
