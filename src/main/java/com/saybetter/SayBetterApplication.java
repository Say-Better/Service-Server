package com.saybetter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.saybetter.global.config")
public class SayBetterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SayBetterApplication.class, args);
	}

}
