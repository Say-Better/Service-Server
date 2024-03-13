package io.say.better.domain.symbol.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.say.better.storage.mysql.dao.repository.SymbolReadRepository;
import io.say.better.storage.mysql.dao.repository.SymbolWriteRepository;
import io.say.better.storage.mysql.domain.entity.Symbol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SymbolService {

	private final SymbolReadRepository symbolReadRepository;
	private final SymbolWriteRepository symbolWriteRepository;

	public List<Symbol> getSymbols(List<String> symbols) {
		return symbolReadRepository.findByTitleIn(symbols);
	}

	public List<Symbol> getSymbols(String name) {
		return symbolReadRepository.findByTitleStartingWith(name);
	}
}
