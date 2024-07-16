package io.say.better.storage.mysql.domains.solution.entity

import io.say.better.storage.mysql.domains.symbol.entity.Symbol
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "SOLUTION_SYMBOL")
class SolutionSymbol(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ss_id")
    private val ssId: Long = 0,
    @JoinColumn(name = "solution_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private val solution: Solution,
    @JoinColumn(name = "symbol_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private val symbol: Symbol,
)
