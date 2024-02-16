package com.saybetter.domain.solution.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.solution.domain.Solution;

public interface SolutionReadRepository extends JpaRepository<Solution, Long> {
}
