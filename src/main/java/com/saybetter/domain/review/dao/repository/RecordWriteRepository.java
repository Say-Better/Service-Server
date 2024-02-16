package com.saybetter.domain.review.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.review.domain.Record;

public interface RecordWriteRepository extends JpaRepository<Record, Long> {
}
