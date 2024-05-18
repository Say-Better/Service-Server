package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Symbol
import org.springframework.data.jpa.repository.JpaRepository

interface SymbolReadRepository : JpaRepository<Symbol, Long> {
    fun findByTitleIn(symbols: List<String>): List<Symbol>

    fun findByTitleStartingWith(name: String): List<Symbol>
}
