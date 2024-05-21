package io.say.better.domain.symbol.application.impl

import io.say.better.storage.mysql.dao.repository.SymbolReadRepository
import io.say.better.storage.mysql.dao.repository.SymbolWriteRepository
import io.say.better.storage.mysql.domain.entity.Symbol
import org.springframework.stereotype.Service

@Service
class SymbolService(
    private val symbolReadRepository: SymbolReadRepository,
    private val symbolWriteRepository: SymbolWriteRepository,
) {
    fun getSymbols(symbols: List<String>?): List<Symbol> {
        return symbolReadRepository.findByTitleIn(symbols!!)
    }

    fun getSymbols(name: String): List<Symbol> {
        return symbolReadRepository.findByTitleStartingWith(name)
    }

    fun getSymbol(symbolId: Long): Symbol {
        return symbolReadRepository.findById(symbolId).get()
    }
}
