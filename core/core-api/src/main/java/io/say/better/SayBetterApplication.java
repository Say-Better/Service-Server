package io.say.better;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SayBetterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SayBetterApplication.class, args);
	}

}
