package io.say.better.client.symbol.api

import io.say.better.client.symbol.api.dto.RecommendRequest
import io.say.better.client.symbol.api.dto.RecommendResult
import io.say.better.client.symbol.converter.RecommendResultConverter
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:client-api.yml")
class RecommendClient (
    private val recommendApi: RecommendApi,

    @Value("\${recommend.api.identity-token}")
    private val identityToken: String
) {

    fun recommend(requestParameter: String): RecommendResult.SymbolRecommend {
        val request = RecommendRequest.SymbolRecommend(requestParameter)

        return RecommendResultConverter.toSymbolRecommend(
            recommendApi.recommend(identityToken, request)!!
        )
    }
}
