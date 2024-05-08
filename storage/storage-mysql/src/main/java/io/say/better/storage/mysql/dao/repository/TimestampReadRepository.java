package io.say.better.storage.mysql.dao.repository;

import io.say.better.storage.mysql.domain.entity.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimestampReadRepository extends JpaRepository<Timestamp, Long> {
}
