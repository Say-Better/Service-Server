package io.say.better.domain.symbol.application.impl

import io.say.better.storage.mysql.dao.repository.SymbolReadRepository
import io.say.better.storage.mysql.dao.repository.SymbolWriteRepository
import io.say.better.storage.mysql.domain.entity.Symbol
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class
SymbolService (
        private val symbolReadRepository: SymbolReadRepository,
        private val symbolWriteRepository: SymbolWriteRepository
) {
    fun getSymbols(symbols: List<String?>?): List<Symbol> {
        return symbolReadRepository!!.findByTitleIn(symbols)
    }

    fun getSymbols(name: String?): List<Symbol> {
        return symbolReadRepository!!.findByTitleStartingWith(name)
    }
}
