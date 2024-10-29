package com.josmejia2401;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
			.getLogger(Application.class);
	
	public static void main(String[] args) {
		LOGGER.info("INICIANDO...");
		SpringApplication.run(Application.class, args);
	}

}
