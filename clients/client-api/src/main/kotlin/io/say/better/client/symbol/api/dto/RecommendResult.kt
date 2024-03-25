package io.say.better.client.symbol.api.dto

class RecommendResult private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class SymbolRecommend(val symbols: List<String>?)
}
