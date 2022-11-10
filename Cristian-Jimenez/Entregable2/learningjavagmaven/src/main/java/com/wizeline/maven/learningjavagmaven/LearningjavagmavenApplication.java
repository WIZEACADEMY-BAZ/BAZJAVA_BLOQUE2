package com.wizeline.maven.learningjavagmaven;


import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.service.UserService;
import com.wizeline.maven.learningjavagmaven.service.UserServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.wizeline.maven.learningjavagmaven.utils.exceptions.ExceptionGenerica;
import java.io.IOException;
import java.util.logging.Logger;


@SpringBootApplication
public class LearningjavagmavenApplication extends Thread {

	private static final Logger LOGGER = Logger.getLogger(LearningjavagmavenApplication.class.getName());
	private static String SUCCESS_CODE="OK000";
	private static String responseTextThread = "";
	private ResponseModel response;
	private static String textThread = "";

	@Override
	public void run(){
		try {
			crearUsuarios();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new ExceptionGenerica(e.getMessage());   }
	}

	private void crearUsuarios() {
		try {

			String user = "user";
			String pass = "password";
			JSONArray jsonArray = new JSONArray(textThread);
			JSONObject userJson;
			ResponseModel response = null;
			LOGGER.info("jsonArray.length(): " + jsonArray.length());
			for(int i = 0; i < jsonArray.length(); i++) {
				userJson = new JSONObject(jsonArray.get(i).toString());
				response = createUser(userJson.getString(user), userJson.getString(pass));
				responseTextThread = new JSONObject(response).toString();
				LOGGER.info("Usuario " + (i+1) + ": " + responseTextThread);
				Thread.sleep(1000);      }
		} catch (InterruptedException e) {
			throw new RuntimeException(e);   }
	}

	private static ResponseModel createUser(String User, String password) {
		UserService userBo = new UserServiceImpl();
		System.out.println("adentro de createUser");
		return userBo.createUser(User, password);
	}
	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavagmavenApplication.class, args);
		System.out.println("inicio spring");
	}

}

