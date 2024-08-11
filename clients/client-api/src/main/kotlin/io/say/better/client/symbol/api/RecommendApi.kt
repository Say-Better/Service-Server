package io.say.better.client.symbol.api

import io.say.better.client.symbol.config.SymbolFeignConfiguration
import io.say.better.client.symbol.dto.RecommendRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "recommend",
    url = "\${recommend.api.base-url}",
    configuration = [SymbolFeignConfiguration::class],
)
internal fun interface RecommendApi {
    @PostMapping("/recommend", produces = ["text/html"])
    fun recommend(
        @RequestHeader("Authorization") identityToken: String,
        @RequestBody request: RecommendRequest.SymbolRecommend,
    ): String
}
