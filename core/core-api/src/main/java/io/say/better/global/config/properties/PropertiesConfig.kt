package io.say.better.global.config.properties

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["io.say.better.global.config.properties"])
open class PropertiesConfig
