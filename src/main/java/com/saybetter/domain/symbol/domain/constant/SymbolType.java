package com.saybetter.domain.symbol.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SymbolType {
	BASIC("기본"),
	ADVANCED("고급");

	private final String description;
}
