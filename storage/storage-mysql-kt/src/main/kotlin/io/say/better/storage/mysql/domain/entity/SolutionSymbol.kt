package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.*

@Entity(name = "SOLUTION_SYMBOL")
class SolutionSymbol(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ss_id")
        private val ssId: Long = 0,

        @JoinColumn(name = "solution_id", nullable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private val solution: Solution,

        @JoinColumn(name = "symbol_id", nullable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private val symbol: Symbol
)
