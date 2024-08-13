package io.say.better.storage.mysql.domains.symbol.repository

import io.say.better.storage.mysql.domains.symbol.entity.Symbol
import org.springframework.data.jpa.repository.JpaRepository

interface SymbolReadRepository : JpaRepository<Symbol, Long> {
    fun findByTitleContaining(title: String): List<Symbol>

    fun findByTitleIn(symbols: List<String>): List<Symbol>

    fun findByTitleStartingWith(name: String): List<Symbol>
}
