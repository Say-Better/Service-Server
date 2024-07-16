package io.say.better.storage.mysql.domains.symbol.repository

import io.say.better.storage.mysql.domains.symbol.entity.Symbol
import org.springframework.data.jpa.repository.JpaRepository

interface SymbolWriteRepository : JpaRepository<Symbol, Long>
