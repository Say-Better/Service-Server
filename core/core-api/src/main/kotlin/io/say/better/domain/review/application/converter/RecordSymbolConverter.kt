package io.say.better.domain.review.application.converter

import io.say.better.domain.solution.ui.dto.SolutionRequest.CreateRecordSymbol
import io.say.better.storage.mysql.domains.review.entity.Record
import io.say.better.storage.mysql.domains.review.entity.RecordSymbol
import io.say.better.storage.mysql.domains.symbol.entity.Symbol
import java.time.LocalDateTime
import java.util.UUID

class RecordSymbolConverter {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toRecordSymbol(
            record: Record,
            id: UUID,
            symbol: Symbol,
            createRecordSymbol: CreateRecordSymbol,
        ): RecordSymbol {
            return RecordSymbol(
                rsId = id,
                recorded = record,
                symbol = symbol,
                touchOrder = createRecordSymbol.touchOrder,
                touchTime = LocalDateTime.parse(createRecordSymbol.touchTime),
            )
        }
    }
}
