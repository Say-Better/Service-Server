package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity(name = "RECORD_SYMBOL")
class RecordSymbol(
        @Id
        @Column(name = "rs_id")
        private var rsId: UUID,

        @JoinColumn(name = "record_id", nullable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private val recorded: Record,

        @JoinColumn(name = "symbol_id", nullable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private val symbol: Symbol,

        @Column(name = "touch_order", nullable = false)
        private val touchOrder: Int,

        @Column(name = "touch_time", nullable = false)
        private val touchTime: LocalDateTime
)
