package com.intland.eurocup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VoucherBackendApp {

	public static void main(String[] args) {
		SpringApplication.run(VoucherBackendApp.class, args);
	}
}
