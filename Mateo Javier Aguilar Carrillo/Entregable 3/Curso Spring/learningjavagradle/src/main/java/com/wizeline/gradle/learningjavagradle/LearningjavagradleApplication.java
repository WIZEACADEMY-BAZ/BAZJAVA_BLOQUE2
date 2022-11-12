package com.wizeline.gradle.learningjavagradle;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LearningjavagradleApplication{


	private static final Logger LOGGER = Logger.getLogger(LearningjavagradleApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(LearningjavagradleApplication.class, args);
		LOGGER.info("LearningJava - Iniciado servicio REST ...");
	}

}
