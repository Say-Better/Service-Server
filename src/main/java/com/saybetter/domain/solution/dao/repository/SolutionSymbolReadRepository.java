package com.saybetter.domain.solution.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.solution.domain.SolutionSymbol;

public interface SolutionSymbolReadRepository extends JpaRepository<SolutionSymbol, Long> {
}
