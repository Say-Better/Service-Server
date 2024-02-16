package com.saybetter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SayBetterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SayBetterApplication.class, args);
	}

}
