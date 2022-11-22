package com.wizeline.gradle.learningjavagradle.controller;

import java.time.Duration;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@RestController
public class ThrottlingController {
	private final Bucket bucket;
    private static final Logger LOGGER = Logger.getLogger(ThrottlingController.class.getName());
    
	public ThrottlingController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
	
    @GetMapping("/throttling")
    public ResponseEntity<String> getUsers() {
        if (bucket.tryConsume(1)) {
            LOGGER.info("Ejemplo throttling 5 peticiones");
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
