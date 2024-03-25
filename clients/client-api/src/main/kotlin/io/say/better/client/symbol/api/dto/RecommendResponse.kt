package io.say.better.client.symbol.api.dto

class RecommendResponse private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class SymbolRecommend(var symbol: String)
}
