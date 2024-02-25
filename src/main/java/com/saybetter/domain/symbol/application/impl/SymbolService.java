package com.saybetter.domain.symbol.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saybetter.domain.symbol.dao.repository.SymbolReadRepository;
import com.saybetter.domain.symbol.dao.repository.SymbolWriteRepository;
import com.saybetter.domain.symbol.domain.Symbol;

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
}
