package com.saybetter.domain.solution.domain;

import com.saybetter.domain.symbol.domain.Symbol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "SOLUTION_SYMBOL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolutionSymbol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ss_id")
	private Long ssId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "solution_id", nullable = false)
	private Solution solution;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbol_id", nullable = false)
	private Symbol symbol;

	@Builder
	public SolutionSymbol(Solution solution, Symbol symbol) {
		this.solution = solution;
		this.symbol = symbol;
	}

}
