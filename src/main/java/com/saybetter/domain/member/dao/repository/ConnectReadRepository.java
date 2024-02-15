package com.saybetter.domain.member.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.member.domain.Connect;

public interface ConnectReadRepository extends JpaRepository<Connect, Long> {
}
