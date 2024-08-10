package io.say.better.client.symbol.converter

import io.say.better.client.symbol.dto.RecommendResult

class RecommendResultConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toSymbolRecommend(recommend: String): RecommendResult.SymbolRecommend =
            RecommendResult.SymbolRecommend(getSymbols(recommend))

        private fun getSymbols(recommendString: String): List<String> {
            val symbols = recommendString.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return ArrayList(listOf(*symbols))
        }
    }
}
