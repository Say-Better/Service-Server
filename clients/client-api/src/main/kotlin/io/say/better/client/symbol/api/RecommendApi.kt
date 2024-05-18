package io.say.better.client.symbol.api

import io.say.better.client.symbol.api.dto.RecommendRequest
import io.say.better.client.symbol.api.dto.RecommendResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "recommend", url = "\${recommend.api.base-url}")
fun interface RecommendApi {
    @PostMapping(value = ["/recommend"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun recommend(
        @RequestHeader("Authorization") identityToken: String,
        @RequestBody request: RecommendRequest.SymbolRecommend,
    ): RecommendResponse.SymbolRecommend
}
