package com.babycenter.pregnancy_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PregnancyTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PregnancyTrackerApplication.class, args);
	}
}