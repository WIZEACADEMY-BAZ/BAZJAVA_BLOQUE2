package com.wizeline.maven.learningjava.Learning;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningApplication extends Thread {

	private static final Logger LOGGER = Logger.getLogger(LearningApplication.class.getName());
   private static final String SUCCESS_CODE = "OK000";
	private static String responseTextThread="";

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningApplication.class, args);
		LOGGER.info("LearningJava - Iniciado servicio REST ...***");
	}

}