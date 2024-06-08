package io.say.better.test.api.symbol

import io.say.better.storage.mysql.dao.repository.SymbolReadRepository
import io.say.better.storage.mysql.dao.repository.SymbolWriteRepository
import io.say.better.storage.mysql.domain.constant.SymbolType
import io.say.better.storage.mysql.domain.entity.Symbol
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class SymbolRepositoryTest
@Autowired constructor(
    private val symbolReadRepository: SymbolReadRepository,
    private val symbolWriteRepository: SymbolWriteRepository
) {
    @Test
    @DisplayName("symbol 이름을 탐색한 결과 리스트를 반환한다. 검색 결과가 없을 경우 빈 리스트를 반환한다.")
    fun getSymbolsByTitleInTest() {
        // Given
        val symbols: List<Symbol> = arrayListOf(
            Symbol(1L, "testTitle1", "testUrl1", SymbolType.BASIC),
            Symbol(2L, "testTitle2", "testUrl2", SymbolType.BASIC),
            Symbol(3L, "testTitle3", "testUrl3", SymbolType.BASIC)
        )
        symbolWriteRepository.saveAll(symbols)

        val symbolNames = arrayListOf(
            "testTitle1",
            "testTitle2",
            "testTitle3"
        )

        // When
        val symbolList: List<Symbol> = symbolReadRepository.findByTitleIn(symbolNames)

        // Then
        Assertions.assertThat(equals(symbols, symbolList)).isEqualTo(true)
    }

    @Nested
    @DisplayName("symbol 이름을 탐색한 결과 리스트를 반환한다. 검색 결과가 없을 경우 빈 리스트를 반환한다.")
    inner class SymbolsByTitleStartingWithTest {
        @Test
        @DisplayName("특정 문자열로 시작하는 symbol 이름을 탐색한 결과 리스트를 반환한다.")
        fun getSymbolsSuccessTest() {
            // Given
            val symbols: List<Symbol> = arrayListOf(
                Symbol(1L, "testTitle1", "testUrl1", SymbolType.BASIC),
                Symbol(2L, "sampleTestTitle2", "testUrl2", SymbolType.BASIC),
                Symbol(3L, "sampleTitle3", "testUrl3", SymbolType.BASIC)
            )
            symbolWriteRepository.saveAll(symbols)

            val symbolNames = "test"

            // When
            val symbolList: List<Symbol> = symbolReadRepository.findByTitleStartingWith(symbolNames)

            // Then
            Assertions.assertThat(
                equals(
                    symbols.filter { it.title.contains(symbolNames) },
                    symbolList
                )
            ).isEqualTo(true)
        }

        @Test
        @DisplayName("symbol 이름에 특정 문자열이 포함되어 있어도 해당 문자열로 시작하지 않는 경우 검색 결과 리스트에 포함하지 않는다.")
        fun getSymbolsFailTest() {
            // Given
            val symbols: List<Symbol> = arrayListOf(
                Symbol(1L, "testTitle1", "testUrl1", SymbolType.BASIC),
                Symbol(2L, "sampletestTitle2", "testUrl2", SymbolType.BASIC),
                Symbol(3L, "sampleTitle3", "testUrl3", SymbolType.BASIC)
            )
            symbolWriteRepository.saveAll(symbols)

            val symbolNames = "test"

            // When
            val symbolList: List<Symbol> = symbolReadRepository.findByTitleStartingWith(symbolNames)

            // Then
            Assertions.assertThat(
                equals(
                    symbols.filter { it.title.contains(symbolNames) },
                    symbolList
                )
            ).isEqualTo(false)
        }
    }

    private fun equals(
        symbols: List<Symbol>,
        comparingSymbols: List<Symbol>
    ): Boolean {
        if (symbols.size != comparingSymbols.size) {
            return false
        }

        for (i in symbols.indices) {
            if (
                symbols[i].title != comparingSymbols[i].title ||
                symbols[i].imgUrl != comparingSymbols[i].imgUrl
            ) {
                return false
            }
        }
        return true
    }
}
