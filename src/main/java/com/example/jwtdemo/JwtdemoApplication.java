package com.example.jwtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
public class JwtdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtdemoApplication.class, args);
	}

}
