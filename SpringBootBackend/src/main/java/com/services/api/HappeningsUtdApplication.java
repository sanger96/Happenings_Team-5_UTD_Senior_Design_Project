package com.services.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.services"})
@EnableScheduling
public class HappeningsUtdApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappeningsUtdApplication.class, args);
	}

}
