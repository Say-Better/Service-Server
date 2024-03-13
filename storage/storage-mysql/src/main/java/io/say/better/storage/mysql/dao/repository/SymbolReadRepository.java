package io.say.better.storage.mysql.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.say.better.storage.mysql.domain.entity.Symbol;

public interface SymbolReadRepository extends JpaRepository<Symbol, Long> {
	List<Symbol> findByTitleIn(List<String> symbols);

	List<Symbol> findByTitleStartingWith(String name);
}
