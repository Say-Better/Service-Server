package io.say.better.storage.mysql.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.say.better.storage.mysql.domain.entity.Connect;

public interface ConnectReadRepository extends JpaRepository<Connect, Long> {
}
