package com.wizeline.maven.learninjavamaven;

import com.sun.net.httpserver.HttpServer;
import com.wizeline.maven.learninjavamaven.config.EndpointBean;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.model.UserDTO;
import com.wizeline.maven.learninjavamaven.service.BankAccountService;
import com.wizeline.maven.learninjavamaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learninjavamaven.service.UserService;
import com.wizeline.maven.learninjavamaven.service.UserServiceImpl;
import com.wizeline.maven.learninjavamaven.utils.Utils;
import com.wizeline.maven.learninjavamaven.utils.exceptions.ExceptionGenerica;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LearninjavamavenApplication extends Thread{

//	private static String responseTextThread = "";
//	private ResponseDTO response;
//	private static String textThread = "";
//
//	private static final String SUCCESS_CODE = "OK000";
//	private static final Logger LOGGER = Logger.getLogger(LearninjavamavenApplication.class.getName());
//
//	private static String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";
//
//	@Autowired
//	private EndpointBean endpointBean;
//
//	@Bean
//	public static UserService userService(){
//		return new UserServiceImpl();
//	}
//
//	public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
//		Map<String, String> query_pairs = new LinkedHashMap<>();
//		String query = uri.getQuery();
//		String[] pairs = query.split("&");
//		for (String pair: pairs){
//			int idx = pair.indexOf("=");
//			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
//					URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
//		}
//		return query_pairs;
//	}
//
//	private static ResponseDTO login(String user, String password){
//		UserService userService = new UserServiceImpl();
//		return userService.login(user, password);
//	}
//
//	private static ResponseDTO createUser(String user, String password){
//		UserService userService = new UserServiceImpl();
//		return  userService.createUser(user, password);
//	}
//
//	private static BankAccountDTO getAccountDetails(String user, String lastUsage){
//		BankAccountService bankAccountService = new BankAccountServiceImpl();
//		return bankAccountService.getAccountDetails(user, lastUsage);
//	}

	public static void main(String[] args){
		SpringApplication.run(LearninjavamavenApplication.class, args);
	}

//
//	public static void main(String[] args) throws IOException {
//
//		SpringApplication.run(LearninjavamavenApplication.class, args);
//
//		LOGGER.info("Learning Java - Iniciando servicio REST ...");
//
//		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//
//		//Hacer login de la app
//		server.createContext("/api/login", (exchange -> {
//			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
//			ResponseDTO response = new ResponseDTO();
//			String responseText = "";
//
//			if("GET".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearningJava - Procesando peticion HTTP GET Login");
//				UserDTO user = new UserDTO();
//				user = user.getParameters(splitQuery(exchange.getRequestURI()));
//				response = login(user.getUser(), user.getPassword());
//				JSONObject json = new JSONObject(response);
//				responseText = json.toString();
//				exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//
//			LOGGER.info("LearningJava - Cerrando cursos");
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));
//
//		//Crear usuario
//		server.createContext("/api/createUser", exchange -> {
//			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
//			ResponseDTO response = new ResponseDTO();
//			String responseText = "";
//
//			exchange.getRequestBody();
//			if("POST".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearningJava Procesamiento peticion HTTP tipo POST");
//				UserDTO user = new UserDTO();
//				user = user.getParameters(splitQuery(exchange.getRequestURI()));
//				response = createUser(user.getUser(), user.getPassword());
//				JSONObject json = new JSONObject(response);
//				responseText = json.toString();
//				exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//
//			LOGGER.info("LearningJava - Cerrando cursos");
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		});
//
//		//Consultar informacion de cuenta de un usuario
//		server.createContext("/api/getUserAccount", (exchange -> {
//			LOGGER.info("LearningJava - Inicia proceso de peticion ...");
//			Instant inicioDeEjecucion = Instant.now();
//			ResponseDTO response = new ResponseDTO();
//
//			String responseText = "";
//
//			if ("GET".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearningJava - Procesando peticion HTTP tipo GET GetUserAccount");
//				UserDTO user = new UserDTO();
//
//				Map<String, String> params = splitQuery(exchange.getRequestURI());
//				user = user.getParameters(params);
//				//Se valida formato del parametro fecha (date) [dd-mm-yyyy]
//
//				String lastUsage = params.get("date");
//				if (Utils.isDateFormatValid(lastUsage)) {
//					//Se valida el password del usuario (password)
//					if (Utils.isPasswordValid(user.getPassword())){
//						response = login(user.getUser(), user.getPassword());
//						if (response.getCode().equals(SUCCESS_CODE)){
//							BankAccountDTO bankAccountDTO = getAccountDetails(user.getUser(), lastUsage);
//							JSONObject json = new JSONObject(bankAccountDTO);
//							responseText = json.toString();
//							exchange.getResponseHeaders().add("Content-type", "application/json");
//							exchange.sendResponseHeaders(200, responseText.getBytes().length);
//						}
//					} else {
//						responseText = "Password Incorrecto";
//						exchange.sendResponseHeaders(401, responseText.getBytes().length);
//					}
//				}
//			} else {
//				responseText = "Formato de fecha incorrecto";
//				exchange.getResponseHeaders().add("Content-type", "application/json");
//				exchange.sendResponseHeaders(400, responseText.getBytes().length);
//			}
//			OutputStream output = exchange.getResponseBody();
//			Instant finalDeEjecucion = Instant.now();
//
//			LOGGER.info("LearningJava - Cerrando cursos ...");
//			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
//			LOGGER.info("Tiempo de respuesta: ".concat(total));
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));
//
//		//Consultar información de todas las cuentas
//		server.createContext("/api/getAccounts", exchange -> {
//			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
//			BankAccountService bankAccountService = new BankAccountServiceImpl();
//
//			String responseText = "";
//
//			if ("GET".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET GetAccounts");
//				List<BankAccountDTO> accounts = bankAccountService.getAccounts();
//				JSONArray json = new JSONArray(accounts);
//				responseText = json.toString();
//				exchange.getResponseHeaders().add("Content-type", "application/json");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//
//			LOGGER.info("LearningJava - Cerrando cursos ...");
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		});
//
//		server.createContext("/api/getAccountByName", exchange -> {
//			LOGGER.info(msgProcPeticion);
//			Instant inicioDeEjecucion = Instant.now();
//			BankAccountService bankAccountService = new BankAccountServiceImpl();
//			String responseText = "";
//			/*Se valida el tipo de peticion request*/
//			if ("GET".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET GetAccountByName");
//				List<BankAccountDTO> accounts = bankAccountService.getAccounts();
//
//				//Aqui se implementara el codigo de filtrar las cuentas por nombre utilizando optional
//				Map<String, String> params = splitQuery(exchange.getRequestURI());
//				Optional<String> Optionalnombre = getParameterValue(params, "name");
//				String nombre = Optionalnombre.get();
//				List<BankAccountDTO> accountsFiltered = bankAccountService.getAccounts();
//				accountsFiltered.clear();
//				for (int i =0; i < accounts.size(); i++ ){
//					if (accounts.get(i).getAccountName().indexOf(nombre)>=0){
//						accountsFiltered.add(accounts.get(i));
//						break;
//					}
//				}
//
//				JSONArray json = new JSONArray(accounts);
//				responseText = json.toString();
//				exchange.getResponseHeaders().add("Content-type", "application/json");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				/* 405 metodo no permitido*/
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//			Instant finalDeEjecucion = Instant.now();
//
//			LOGGER.info("LearningJava - Cerrando recursos ...");
//			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion)
//					.toMillis()).concat(" segundos."));
//			LOGGER.info("Tiempo de respuesta: ".concat(total));
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		});
//
//		//Consulta todas las cuentas y filtrar por usuario
//		server.createContext("/api/getAccountsByUser", exchange -> {
//			LOGGER.info(msgProcPeticion);
//			Instant inicioDeEjecucion = Instant.now();
//			BankAccountService bankAccountService = new BankAccountServiceImpl();
//			String responseText = "";
//			/*Se valida el tipo de request HTTP*/
//			if ("GET".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST GetAccountsByUser");
//				List<BankAccountDTO> accounts = bankAccountService.getAccounts();
//				List<BankAccountDTO> accountsFiltered = bankAccountService.getAccounts();
//				accountsFiltered.clear();
//
//				// Aqui se implementa el codigo para filtrar las cuentas por usuario usando genericos
//				Map<String, String > params = splitQuery(exchange.getRequestURI());
//				Optional<Object> Optionaluser = getParameterValueObject(params, "user");
//				Object user = Optionaluser.get();
//				for (int i = 0; i < accounts.size(); i ++){
//					if (accounts.get(i).getUser().indexOf(user.toString()) >= 0){
//						accountsFiltered.add(accounts.get(i));
//					}
//				}
//
//				JSONArray json = new JSONArray(accountsFiltered);
//				responseText = json.toString();
//				exchange.getResponseHeaders().add("Content-type", "application/json");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				/*405 metodo no permitido*/
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//			Instant finalDeEjecucion = Instant.now();
//
//
//			LOGGER.info("LearningJava - Cerrando recursos ...");
//			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
//			LOGGER.info("Tiempo de respuesta: ".concat(total));
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		});
//
//		server.createContext("/api/createUsers", exchange -> {
//			LOGGER.info("LearninJava iniciando peticion ...");
//			ResponseDTO response = new ResponseDTO();
//			/*Se valida el tipo de request HTTP*/
//			exchange.getRequestBody();
//			if ("POST".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST CreateUsers");
//				/*Se obtiene el request del body que se mando*/
//				StringBuilder text = new StringBuilder();
//				try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
//					while (scanner.hasNext()){
//						text.append(scanner.next());
//					}
//				} catch (Exception e){
//					LOGGER.severe(e.getMessage());
//					throw new ExceptionGenerica("Fallo al obtener el request del body");
//				}
//				textThread = text.toString();
//				LOGGER.info(textThread);
//				//Se inicia el thread
//				LearninjavamavenApplication thread = new LearninjavamavenApplication();
//				thread.start();
//				//Esperamos a que termine el thread
//				while (thread.isAlive());
//				exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
//				exchange.sendResponseHeaders(200, responseTextThread.getBytes().length);
//			} else {
//				/*405 Metodo no permitido*/
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//
//			LOGGER.info("LearningJava - Cerrando cursos ...");
//			output.write(responseTextThread.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		});
//
//		//Consulta todas las cuentas y agruparlas por su tipo utilizando Programacion Funcional
//		server.createContext("/api/getAccountsGroupByType", (exchange -> {
//			LOGGER.info(msgProcPeticion);
//			Instant inicioDeEjecucion =Instant.now();
//			BankAccountService bankAccountService = new BankAccountServiceImpl();
//			String responseText ="";
//			/*Valida los tipos de datos del request*/
//			if("GET".equals(exchange.getRequestMethod())){
//				LOGGER.info("LearninJava - procesando peticion HTTP de tipo GET GetAccountsGroupByType");
//				List<BankAccountDTO> accounts = bankAccountService.getAccounts();
//
//				//Aqui se implementara la pogramacion funcional
//				Map<String, List<BankAccountDTO> > groupedAccounts;
//				Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
//				groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
//
//				JSONArray josn = new JSONArray(groupedAccounts);
//				responseText = josn.toString();
//				exchange.getResponseHeaders().add("Content-type", "application/json");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				/*405 Metodo no permitido*/
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//			Instant finalDeEjecucion = Instant.now();
//
//			LOGGER.info("LearningJava - Cerrando cursos ...");
//			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat("segundos"));
//			LOGGER.info("Tiempo de respuesta: ".concat(total));
//			output.write(responseTextThread.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));
//
//
//		// Consultar todas las cuentas y regresarselas al usuario de manera cifrada
//		server.createContext("/api/getEncryptedAccounts", (exchange -> {
//			LOGGER.info(msgProcPeticion);
//			Instant inicioDeEjecucion = Instant.now();
//			BankAccountService bankAccountService = new BankAccountServiceImpl();
//			String responseText = "";
//			/** Validates the type of http request  */
//			if ("GET".equals(exchange.getRequestMethod())) {
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
//				List<BankAccountDTO> accounts = bankAccountService.getAccounts();
//
//				// Aquí implementaremos nuestro código de cifrar nuestras cuentas y regresarselas al usuario de manera cifrada
//				byte[] keyBytes = new byte[]{
//						0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
//				};
//				byte[] ivBytes = new byte[]{
//						0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
//				};
//				Security.addProvider(new BouncyCastleProvider());
//				SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
//				IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//				Cipher cipher = null;
//				try {
//					cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
//					cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
//					// Cifraremos solamente el nombre y el country (pueden cifrar todos los parámetros que gusten)
//					for (int i = 0; i < accounts.size(); i++) {
//						String accountName = accounts.get(i).getAccountName();
//						byte[] arrAccountName = accountName.getBytes();
//						byte [] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
//						int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
//						ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
//						accounts.get(i).setAccountName(accountNameCipher.toString());
//
//						String accountCountry = accounts.get(i).getCountry();
//						byte[] arrAccountCountry = accountCountry.getBytes();
//						byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
//						int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
//						ctAccountNameLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
//						accounts.get(i).setCountry(accountCountryCipher.toString());
//
//					}
//				} catch (NoSuchAlgorithmException e) {
//					throw new RuntimeException(e);
//				} catch (NoSuchProviderException e) {
//					throw new RuntimeException(e);
//				} catch (NoSuchPaddingException e) {
//					throw new RuntimeException(e);
//				} catch (InvalidAlgorithmParameterException e) {
//					throw new RuntimeException(e);
//				} catch (ShortBufferException e) {
//					throw new RuntimeException(e);
//				} catch (IllegalBlockSizeException e) {
//					throw new RuntimeException(e);
//				} catch (BadPaddingException e) {
//					throw new RuntimeException(e);
//				} catch (InvalidKeyException e) {
//					throw new RuntimeException(e);
//				}
//
//
//				JSONArray json = new JSONArray(accounts);
//				responseText = json.toString();
//				exchange.getResponseHeaders().add("Content-type", "application/json");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				/** 405 Method Not Allowed */
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//			Instant finalDeEjecucion = Instant.now();
//			/**
//			 * Always remember to close the resources you open.
//			 * Avoid memory leaks
//			 */
//			LOGGER.info("LearningJava - Cerrando recursos ...");
//			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
//			LOGGER.info("Tiempo de respuesta: ".concat(total));
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));
//
//		server.setExecutor(null);
//		server.start();
//		LOGGER.info("LearningJava - Server started on port 8080");
//	}
//
//
//	private static Optional<String> getParameterValue(Map<String, String> param, String paramName){
//		String val = param.get(paramName);
//		if (val != null && val != ""){
//			return Optional.ofNullable(val);
//		}
//		return Optional.ofNullable("NA");
//	}
//
//	@Override
//	public void run(){
//		try {
//			crearUsuarios();
//		} catch (Exception e) {
//			LOGGER.severe(e.getMessage());
//			throw new ExceptionGenerica(e.getMessage());
//		}
//	}
//
//	@Deprecated(since = "Anotaciones update")
//	private void createUsers() {
//		try {
//			String user = "user";
//			String pass = "password";
//			JSONArray jsonArray = new JSONArray(textThread);
//			JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
//			JSONObject user2 = new JSONObject(jsonArray.get(0).toString());
//			JSONObject user3 = new JSONObject(jsonArray.get(0).toString());
//			//Se crea usuario 1
//			response = createUser(user1.getString(user), user1.getString(pass));
//			responseTextThread = new JSONObject(response).toString();
//			LOGGER.info("Usuario 1: " + responseTextThread);
//			Thread.sleep(1000);
//			//Se crea usuario 2
//			response = createUser(user2.getString(user), user2.toString());
//			responseTextThread = new JSONObject(response).toString();
//			LOGGER.info("Usuario 2: " + responseTextThread);
//			Thread.sleep(1000);
//			//Se crea usuario 3
//			response = createUser(user3.getString(user), user3.getString(pass));
//			responseTextThread = new  JSONObject(response).toString();
//			LOGGER.info("Usuario 3: " + responseTextThread);
//			Thread.sleep(1000);
//		} catch (InterruptedException e){
//			throw new RuntimeException(e);
//		}
//	}
//
//	private void crearUsuarios() {
//		try {
//			String user = "user";
//			String pass = "password";
//			JSONArray jsonArray = new JSONArray(textThread);
//			JSONObject userJson;
//
//			ResponseDTO response = null;
//
//			LOGGER.info("jsonArray.length(): " + jsonArray.length());
//			for(int i = 0; i < jsonArray.length(); i++) {
//				userJson = new JSONObject(jsonArray.get(i).toString());
//				response = createUser(userJson.getString(user), userJson.getString(pass));
//				responseTextThread = new JSONObject(response).toString();
//				LOGGER.info("Usuario " + (i+1) + ": " + responseTextThread);
//				Thread.sleep(1000);
//			}
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private static Optional<Object> getParameterValueObject(Map<String, String> param, String paramName){
//		String val = param.get(paramName);
//		if (val != null && val != ""){
//			return Optional.ofNullable(val);
//		}
//		return Optional.ofNullable("NA");
//	}

}
