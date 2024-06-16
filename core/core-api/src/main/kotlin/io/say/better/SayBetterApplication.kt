package io.say.better

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@OpenAPIDefinition(servers = [Server(url = "https://www.saybetter.store", description = "Dev")])
@SpringBootApplication
@ConfigurationPropertiesScan
class SayBetterApplication

fun main(args: Array<String>) {
    runApplication<SayBetterApplication>(*args)
}
