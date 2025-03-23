package com.example.rickmotyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RickmotyapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickmotyapiApplication.class, args);
	}
}
