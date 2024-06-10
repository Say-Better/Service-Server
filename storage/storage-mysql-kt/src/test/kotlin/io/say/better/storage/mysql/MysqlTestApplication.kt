package io.say.better.storage.mysql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class MysqlTestApplication

fun main(args: Array<String>) {
    runApplication<MysqlTestApplication>(*args)
}
