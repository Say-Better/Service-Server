package io.say.better.domain.review.application.impl;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.ReviewReadRepository;
import io.say.better.storage.mysql.dao.repository.ReviewWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewReadRepository reviewReadRepository;
	private final ReviewWriteRepository reviewWriteRepository;

}
