package baz.practice.wizeline.learningjavamaven;

import baz.practice.wizeline.learningjavamaven.config.EndpointBean;
import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.model.UserDTO;
import baz.practice.wizeline.learningjavamaven.service.BankAccountBO;
import baz.practice.wizeline.learningjavamaven.service.BankAccountBOImpl;
import baz.practice.wizeline.learningjavamaven.service.UserBO;
import baz.practice.wizeline.learningjavamaven.service.UserBOImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sun.net.httpserver.HttpServer;
/*
import com.wizeline.BO.BankAccountBO;
import com.wizeline.BO.BankAccountBOImpl;
import com.wizeline.BO.UserBO;
import com.wizeline.BO.UserBOImpl;
import com.wizeline.DTO.BankAccountDTO;
import com.wizeline.DTO.ResponseDTO;
import com.wizeline.DTO.UserDTO;
 */
import baz.practice.wizeline.learningjavamaven.utils.exceptions.ExceptionGenerica;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.security.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static baz.practice.wizeline.learningjavamaven.utils.Utils.isDateFormatValid;
import static baz.practice.wizeline.learningjavamaven.utils.Utils.isPasswordValid;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LearningjavamavenApplication extends Thread {

	private static String SUCCESS_CODE = "OK000";

	private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());
	private static String responseTextThread = "";
	private ResponseDTO response;
	private static String textThread = "";

	@Override
	public void run(){
		try {
			crearUsuarios();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new ExceptionGenerica(e.getMessage());
		}
	}

	private void crearUsuarios() {
		try {
			String user = "user";
			String pass = "password";
			JSONArray jsonArray = new JSONArray(textThread);
			JSONObject userJson;

			ResponseDTO response = null;

			LOGGER.info("jsonArray.length(): " + jsonArray.length());
			for(int i = 0; i < jsonArray.length(); i++) {
				userJson = new JSONObject(jsonArray.get(i).toString());
				response = createUser(userJson.getString(user), userJson.getString(pass));
				responseTextThread = new JSONObject(response).toString();
				LOGGER.info("Usuario " + (i+1) + ": " + responseTextThread);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static ResponseDTO createUser(String User, String password){
		UserBO userBo = new UserBOImpl();
		return userBo.createUser(User, password);
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavamavenApplication.class, args);}
}
