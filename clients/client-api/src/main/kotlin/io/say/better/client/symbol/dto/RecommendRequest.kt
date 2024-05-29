package io.say.better.client.symbol.dto

class RecommendRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class SymbolRecommend(val name: String)
}
