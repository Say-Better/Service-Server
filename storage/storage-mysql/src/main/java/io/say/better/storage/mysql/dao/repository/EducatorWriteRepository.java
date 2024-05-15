package io.say.better.storage.mysql.dao.repository;

import io.say.better.storage.mysql.domain.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducatorWriteRepository extends JpaRepository<Educator, Long> {
}
