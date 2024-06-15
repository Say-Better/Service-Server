package io.say.better.domain.review.application

import io.say.better.core.common.utils.logger
import io.say.better.domain.review.application.converter.RecordSymbolConverter
import io.say.better.domain.review.application.impl.RecordService
import io.say.better.domain.review.application.impl.RecordSymbolService
import io.say.better.domain.review.application.impl.ReviewService
import io.say.better.domain.solution.application.converter.RecordConverter
import io.say.better.domain.solution.ui.dto.SolutionRequest.EndSolution
import io.say.better.domain.symbol.application.impl.SymbolService
import io.say.better.global.advice.Tx
import io.say.better.global.utils.IdUtil
import io.say.better.storage.mysql.domain.entity.Record
import io.say.better.storage.mysql.domain.entity.Review
import io.say.better.storage.mysql.domain.entity.Symbol
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ReviewFacade(
    private val reviewService: ReviewService,
    private val recordService: RecordService,
    private val symbolService: SymbolService,
    private val recordSymbolService: RecordSymbolService,
    private val idUtil: IdUtil,
) {
    private val log = logger()

    @RabbitListener(queues = ["solution.queue"], ackMode = "AUTO")
    fun recordSubscriber(endSolution: EndSolution) =
        Tx.writeable {
            val review: Review = reviewService.getReview(endSolution.reviewId!!)

            val record: Record = RecordConverter.toRecord(endSolution, review)
            val savedRecord: Record = recordService.createRecord(record)

            for (createRecordSymbol in endSolution.createRecordSymbols) {
                val symbol: Symbol = symbolService.getSymbol(createRecordSymbol.symbolId)
                val recordSymbol =
                    RecordSymbolConverter.toRecordSymbol(
                        savedRecord,
                        idUtil.getUUID(),
                        symbol,
                        createRecordSymbol,
                    )

                recordSymbolService.createRecordSymbol(recordSymbol)
            }
        }
}
