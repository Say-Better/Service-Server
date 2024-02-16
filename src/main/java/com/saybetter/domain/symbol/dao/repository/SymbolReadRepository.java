package com.saybetter.domain.symbol.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saybetter.domain.symbol.domain.Symbol;

public interface SymbolReadRepository extends JpaRepository<Symbol, Long> {
}
