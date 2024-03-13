package io.say.better.storage.mysql.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.say.better.storage.mysql.domain.entity.RecordSymbol;

public interface RecordSymbolReadRepository extends JpaRepository<RecordSymbol, Long> {
}
