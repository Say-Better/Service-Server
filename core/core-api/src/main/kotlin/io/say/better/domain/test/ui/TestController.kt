package io.say.better.domain.test.ui

import io.say.better.global.common.response.ResponseDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Test", description = "Test API")
@RestController
@RequestMapping("/api/tests")
class TestController constructor(){

    @GetMapping("")
    fun getTests(): ResponseDto<String> {
        return ResponseDto.onSuccess("Tests")
    }

}
