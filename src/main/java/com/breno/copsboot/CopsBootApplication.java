package com.breno.copsboot;

import java.util.UUID;

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
	public UniqueIdGenerator<UUID> uniqueIdGenerator() {
		return new InMemoryUniqueIdGenerator();
	}
}
