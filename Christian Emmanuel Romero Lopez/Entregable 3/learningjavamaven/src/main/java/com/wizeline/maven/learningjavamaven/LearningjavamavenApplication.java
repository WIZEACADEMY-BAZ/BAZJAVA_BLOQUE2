package com.wizeline.maven.learningjavamaven;

import java.io.IOException;

import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import com.wizeline.maven.learningjavamaven.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LearningjavamavenApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavamavenApplication.class, args);
	}

	@Bean
	public static UserService userService() {
		return new UserServiceImpl();
	}
	@Bean
	public static BankAccountService bankAccountService(){
		return new BankAccountServiceImpl();
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
