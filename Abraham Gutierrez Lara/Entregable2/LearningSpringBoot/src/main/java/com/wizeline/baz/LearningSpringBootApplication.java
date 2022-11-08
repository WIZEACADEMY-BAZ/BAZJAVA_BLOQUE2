package com.wizeline.baz;

import java.security.Security;
import java.util.logging.Logger;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class LearningSpringBootApplication extends Thread {

	private static final Logger LOGGER = Logger.getLogger(LearningSpringBootApplication.class.getName());
	
	static  {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static void main(String[] args) {

		SpringApplication.run(LearningSpringBootApplication.class, args);
	}
}
