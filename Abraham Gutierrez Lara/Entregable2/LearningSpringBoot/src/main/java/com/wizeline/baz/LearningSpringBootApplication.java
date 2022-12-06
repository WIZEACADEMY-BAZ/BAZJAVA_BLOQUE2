package com.wizeline.baz;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.annotation.EnableKafka;

@EnableAspectJAutoProxy
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableKafka
@EnableBatchProcessing
public class LearningSpringBootApplication {
	
	static  {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LearningSpringBootApplication.class, args);
	}
}
