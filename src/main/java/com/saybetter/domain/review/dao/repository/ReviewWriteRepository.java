package com.saybetter.domain.review.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.review.domain.Review;

public interface ReviewWriteRepository extends JpaRepository<Review, Long> {
}
