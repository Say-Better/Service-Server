package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Symbol
import org.springframework.data.jpa.repository.JpaRepository

interface SymbolWriteRepository : JpaRepository<Symbol, Long>
