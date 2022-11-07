package com.wizeline.learningspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Logger;

@SpringBootApplication
public class LearningspringApplication extends Thread{
    private static final Logger LOGGER = Logger.getLogger(LearningspringApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(LearningspringApplication.class, args);
        LOGGER.info("Iniciado servicio REST ...");
    }

}
