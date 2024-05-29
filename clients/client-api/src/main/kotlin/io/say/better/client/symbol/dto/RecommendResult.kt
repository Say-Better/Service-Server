package io.say.better.client.symbol.dto

class RecommendResult private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class SymbolRecommend(val symbols: List<String>?)
}
