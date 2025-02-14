package com.eeerrorcode.pilllaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // regDate, modDate 적용 어노테이션
public class PilllawApplication {

	public static void main(String[] args) {
		SpringApplication.run(PilllawApplication.class, args);
	}

}
