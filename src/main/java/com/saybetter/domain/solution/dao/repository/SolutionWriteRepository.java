package com.saybetter.domain.solution.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.solution.domain.Solution;

public interface SolutionWriteRepository extends JpaRepository<Solution, Long> {
}
