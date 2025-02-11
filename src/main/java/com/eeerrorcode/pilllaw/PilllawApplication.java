package com.eeerrorcode.pilllaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PilllawApplication {

	public static void main(String[] args) {
		SpringApplication.run(PilllawApplication.class, args);
	}

}
