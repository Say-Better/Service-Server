package io.say.better.client.symbol.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.saybetter.client.api")
public class OpenFeignConfig {
}
