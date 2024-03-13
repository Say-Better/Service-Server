package io.say.better.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "com.saybetter.global.config.properties")
public class PropertiesConfig {
}
