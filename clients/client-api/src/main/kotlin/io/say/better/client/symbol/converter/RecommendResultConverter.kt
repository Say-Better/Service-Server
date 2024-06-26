package io.say.better.client.symbol.converter

import io.say.better.client.symbol.dto.RecommendResponse
import io.say.better.client.symbol.dto.RecommendResult

class RecommendResultConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toSymbolRecommend(recommend: RecommendResponse.SymbolRecommend): RecommendResult.SymbolRecommend {
            return RecommendResult.SymbolRecommend(getSymbols(recommend))
        }

        private fun getSymbols(recommend: RecommendResponse.SymbolRecommend): List<String> {
            val symbolString = recommend.symbol
            val symbols = symbolString.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return ArrayList(listOf(*symbols))
        }
    }
}
