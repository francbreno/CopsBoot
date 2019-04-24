package com.breno.copsboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.breno.copsboot.orm.jpa.InMemoryUniqueIdGenerator;
import com.breno.copsboot.orm.jpa.UniqueIdGenerator;

@SpringBootApplication
public class CopsBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopsBootApplication.class, args);
	}

	@Bean
	public UniqueIdGenerator uniqueIdGenerator() {
		return new InMemoryUniqueIdGenerator();
	}
}
