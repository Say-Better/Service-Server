package com.saybetter.domain.solution.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.solution.domain.Assign;

public interface AssignReadRepository extends JpaRepository<Assign, Long> {
}
