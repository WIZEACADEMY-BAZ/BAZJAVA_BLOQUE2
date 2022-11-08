package com.wizeline.maven.learningjavagmaven;


import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Logger;


@SpringBootApplication
public class LearningjavagmavenApplication extends Thread {

	private static final Logger LOGGER = Logger.getLogger(LearningjavagmavenApplication.class.getName());
	private static String SUCCESS_CODE="OK000";
	private static String responseTextThread = "";
	private ResponseModel response;
	private static String textThread = "";

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavagmavenApplication.class, args);
		System.out.println("inicio spring");
	}

}

