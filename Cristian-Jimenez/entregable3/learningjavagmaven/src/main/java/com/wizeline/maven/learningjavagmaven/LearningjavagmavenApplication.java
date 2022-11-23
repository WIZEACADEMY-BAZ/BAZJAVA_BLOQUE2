package com.wizeline.maven.learningjavagmaven;
import com.wizeline.maven.learningjavagmaven.FactoryMeth.PruebaFactoryMeth;
import com.wizeline.maven.learningjavagmaven.Observer.Cliente;
import com.wizeline.maven.learningjavagmaven.Observer.Observadores.Observador;
import com.wizeline.maven.learningjavagmaven.singleton.PruebaSingletonThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.java.JavaLoggingSystem;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

import javax.validation.constraints.Null;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class LearningjavagmavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningjavagmavenApplication.class, args);
		System.out.println("inicio spring");
		PruebaSingletonThread.PruebaSingleton();
		PruebaFactoryMeth.PruebaFactoryMeth();
		Cliente.clienteObservador();
	}

}

