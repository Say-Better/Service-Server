package com.saybetter.domain.solution.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.solution.domain.Assign;

public interface AssignWriteRepository extends JpaRepository<Assign, Long> {
}
