package com.wizeline.maven.learningjava.Learning;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableFeignClients

public class LearningApplication{

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningApplication.class, args);
	}

}