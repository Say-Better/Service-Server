package com.saybetter.domain.review.domain;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity(name = "RECORD_SYMBOL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordSymbol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rs_id")
	private Long rsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id", nullable = false)
	private Record record;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbol_id", nullable = false)
	private Symbol symbol;

	@Column(name = "touch_count", nullable = false, columnDefinition = "int default 0")
	private Integer touchCount;

}
