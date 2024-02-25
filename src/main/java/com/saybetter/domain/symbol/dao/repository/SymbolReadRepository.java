package com.saybetter.domain.symbol.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.symbol.domain.Symbol;

public interface SymbolReadRepository extends JpaRepository<Symbol, Long> {
	List<Symbol> findByTitleIn(List<String> symbols);

	List<Symbol> findByTitleStartingWith(String name);
}
