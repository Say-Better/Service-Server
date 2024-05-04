package io.say.better.storage.mysql.domain.entity;

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

import java.time.LocalDateTime;

@Slf4j
@Getter
@Entity(name = "RECORD_SYMBOL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordSymbol {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "rs_id")
	private String rsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id", nullable = false)
	private Record recorded;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbol_id", nullable = false)
	private Symbol symbol;

    @Column(name = "touch_order", nullable = false)
    private Integer touchOrder;

    @Column(name = "touch_time", nullable = false)
    private LocalDateTime touchTime;

}
