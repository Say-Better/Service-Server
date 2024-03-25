package io.say.better.client.symbol.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["io.say.better.client.symbol.api"])
open class OpenFeignConfig
