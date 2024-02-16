package com.saybetter.domain.review.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.review.domain.RecordSymbol;

public interface RecordSymbolWriteRepository extends JpaRepository<RecordSymbol, Long> {
}
