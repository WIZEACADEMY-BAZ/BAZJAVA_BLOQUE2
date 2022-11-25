package com.wizeline.maven.learningjavamaven;

import com.wizeline.maven.learningjavamaven.config.EndpointBean;
import com.wizeline.maven.learningjavamaven.factory.NotificationFactory;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.notifications.Notification;
import com.wizeline.maven.learningjavamaven.service.UserService;
import com.wizeline.maven.learningjavamaven.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import java.io.IOException;
import java.util.logging.Logger;


//Concurrencia
@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class LearningjavamavenApplication extends Thread{
	private static String responseTextThread = "";
	private ResponseDTO response;
	private static String textThread = "";
	private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());
	private static String SUCCESS_CODE = "OK000";

	@Autowired
	private EndpointBean endpointBean;

	@Bean
	public static UserService userService() {
		return new UserServiceImpl();
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavamavenApplication.class, args);
		NotificationFactory notificationFactory = new NotificationFactory();
		Notification notification = notificationFactory.createNotification("SMS");
		notification.notifyUser();



	}
}

