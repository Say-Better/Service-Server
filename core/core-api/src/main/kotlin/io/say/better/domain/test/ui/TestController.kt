package io.say.better.domain.test.ui

import io.say.better.core.common.response.ResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tests")
class TestController constructor() {
    @GetMapping("")
    fun getTests(): ResponseDto<String> = ResponseDto.onSuccess("Tests")
}
