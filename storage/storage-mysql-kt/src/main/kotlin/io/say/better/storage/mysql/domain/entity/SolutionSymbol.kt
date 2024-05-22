package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

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
    private val symbol: Symbol,
)
