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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Getter
@Entity(name = "RECORD_SYMBOL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordSymbol {

	@Id
	@Column(name = "rs_id")
	private UUID rsId;

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

    @Builder
    public RecordSymbol(UUID rsId, Record recorded, Symbol symbol, Integer touchOrder, LocalDateTime touchTime) {
        this.rsId = rsId;
        this.recorded = recorded;
        this.symbol = symbol;
        this.touchOrder = touchOrder;
        this.touchTime = touchTime;
    }
}
