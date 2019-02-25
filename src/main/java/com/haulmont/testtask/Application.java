package com.haulmont.testtask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		LOGGER.info("HaulmontLOG4J2: Application started...");
		SpringApplication.run(Application.class, args);
	}

}

