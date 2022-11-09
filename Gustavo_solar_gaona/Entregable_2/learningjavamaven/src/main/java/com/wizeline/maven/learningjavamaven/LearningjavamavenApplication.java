package com.wizeline.maven.learningjavamaven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.wizeline.maven.learningjavamaven.configuration.EndpointBean;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import com.wizeline.maven.learningjavamaven.service.UserServiceImpl;
import com.wizeline.maven.learningjavamaven.utils.Utils;
import com.wizeline.maven.learningjavamaven.utils.exceptions.ExcepcionGenerica;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;
import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

@SpringBootApplication
public class LearningjavamavenApplication extends Thread {

	private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());
	private static String responseTextThread = "";
	private static String textThread = "";

	@Autowired
	private EndpointBean endpointBean;

	/*@Bean
	public static UserService userService() {
		return new UserServiceImpl();
	}*/

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavamavenApplication.class, args);

		LOGGER.info("LearningJava - Iniciado servicio REST ...");
		HttpServer server = HttpServer.create(new InetSocketAddress(SOCKET_PORT), DEFAULT_BACKLOG);
    	/*
    	Estos métodos fueron reemplazados por sus equivalentes utilizando springboot
		// logear usuario
		server.createContext("/api/login", (exchange -> iniciarSesion(exchange)));

		// crear usuario
		server.createContext("/api/createUser", (exchange -> crearUsuario(exchange)));

		// Crear usuarios
		server.createContext("/api/createUsers", (exchange -> crearUsuariosEndPoint(exchange)));
		*/

		// Consultar información de cuenta de un usuario
		server.createContext("/api/getUserAccount", (exchange -> getAccount(exchange)));
		// Se puede remplazar la lambda por la siguiente forma
		//server.createContext("/api/getUserAccount", (LearningjavamavenApplication::getAccount));

		// Consultar información de todas las cuentas
		server.createContext("/api/getAccounts", (exchange -> getAccounts(exchange)));

		// Consultar todas las cuentas y buscarla por nombre utilizando Optional por si no es encontrada
		server.createContext("/api/getAccountByName", (exchange -> getAccountByName(exchange)));

		// Consultar todas las cuentas y filtrarlas por usuario
		server.createContext("/api/getAccountsByUser", (exchange -> getAccountByUser(exchange)));

		// Consultar todas las cuentas y agruparlas por su tipo utilizando Programación Funcional
		server.createContext("/api/getAccountsGroupByType", (exchange -> getAccountsGroupByType(exchange)));

		// Consultar todas las cuentas y regresarselas al usuario de manera cifrada
		server.createContext("/api/getEncryptedAccounts", (exchange -> getEncryptedAccounts(exchange)));

		server.setExecutor(null);
		server.start();
		LOGGER.info(SERVER_STARTED);
	}

	private static void iniciarSesion(HttpExchange exchange) throws IOException {
		LOGGER.info(INIT_PROCESS);
		ResponseDTO response;
		String responseText = "";
		if ("GET".equals(exchange.getRequestMethod())) {
			LOGGER.info(PROCESSING_GET_METHOD);
			UserDTO user = new UserDTO();
			user = user.getParameters(splitQuery(exchange.getRequestURI()));
			response = login(user.getUser(), user.getPassword());
			JSONObject json = new JSONObject(response);
			responseText = json.toString();
			exchange.getResponseHeaders().set(CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();

		LOGGER.info(CLOSING_RESOURCES);
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static void crearUsuario(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		ResponseDTO response;
		String responseText = "";
		exchange.getRequestBody();
		if (POST_METHOD.equals(exchange.getRequestMethod())) {

			StringBuilder text = new StringBuilder();
			try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
				while(scanner.hasNext()) {
					text.append(scanner.next());
				}
			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
				throw new ExcepcionGenerica("Fallo al obtener el request del body");
			}
			LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Create user");

			ObjectMapper objectMapper = new ObjectMapper();
			UserDTO user = objectMapper.readValue(text.toString(), UserDTO.class);

			response = createUser(user.getUser(), user.getPassword());
			JSONObject json = new JSONObject(response);
			responseText = json.toString();
			exchange.getResponseHeaders().set(CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();

		LOGGER.info(CLOSING_RESOURCES);
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static void crearUsuariosEndPoint(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		ResponseDTO response = new ResponseDTO();
		exchange.getRequestBody();
		if (POST_METHOD.equals(exchange.getRequestMethod())) {
			LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST");

			StringBuilder text = new StringBuilder();
			try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
				while(scanner.hasNext()) {
					text.append(scanner.next());
				}
			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
				throw new ExcepcionGenerica("Fallo al obtener el request del body");
			}
			textThread = text.toString();

			LOGGER.info(textThread);
			LearningjavamavenApplication thread = new LearningjavamavenApplication();
			thread.start();

			while(thread.isAlive());

			exchange.getResponseHeaders().set(CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseTextThread.getBytes().length);
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();

		LOGGER.info(CLOSING_RESOURCES);
		output.write(responseTextThread.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static void getAccount(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		Instant inicioDeEjecucion = Instant.now();
		ResponseDTO response;

		String responseText = "";
		if (GET_METHOD.equals(exchange.getRequestMethod())) {
			LOGGER.info(PROCESSING_GET_METHOD);
			UserDTO user =  new UserDTO();
			Map<String, String> params = splitQuery(exchange.getRequestURI());
			user = user.getParameters(params);
			String lastUsage = params.get("date");
			if (Utils.isDateFormatValid(lastUsage)) {
				if (Utils.isPasswordValid(user.getPassword())) {
					response = login(user.getUser(), user.getPassword());
					System.out.println(response.getCode());
					if (response.getCode().equals(SUCCESS_CODE)) {
						BankAccountDTO bankAccountDTO = getAccountDetails(user.getUser(), lastUsage);
						JSONObject json = new JSONObject(bankAccountDTO);
						responseText = json.toString();
						exchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TYPE_JSON);
						exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
					}else{
						throw new ExcepcionGenerica("No se encontró el usuario");
					}
				} else {
					responseText = "Password Incorrecto";
					exchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TYPE_JSON);
					exchange.sendResponseHeaders(UNAUTHORIZED_CODE, responseText.getBytes().length);
				}
			} else {
				responseText = "Formato de Fecha Incorrecto";
				exchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TYPE_JSON);
				exchange.sendResponseHeaders(BAD_REQUEST_CODE, responseText.getBytes().length);
			}
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();
		Instant finalDeEjecucion = Instant.now();

		LOGGER.info(CLOSING_RESOURCES);
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
		LOGGER.info(TIME_OF_RESPONSE.concat(total));
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static void getAccounts(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		Instant inicioDeEjecucion = Instant.now();
		BankAccountService bankAccountBO = new BankAccountServiceImpl();
		String responseText = "";
		if (GET_METHOD.equals(exchange.getRequestMethod())) {
			LOGGER.info(PROCESSING_GET_METHOD);
			List<BankAccountDTO> accounts = bankAccountBO.getAccountsLocal();
			JSONArray json = new JSONArray(accounts);
			responseText = json.toString();
			exchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TYPE_JSON);
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();
		Instant finalDeEjecucion = Instant.now();

		LOGGER.info(CLOSING_RESOURCES);
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
		LOGGER.info(TIME_OF_RESPONSE.concat(total));
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static void getAccountByName(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		Instant inicioDeEjecucion = Instant.now();
		BankAccountService bankAccountService = new BankAccountServiceImpl();
		String responseText = "";
		if("GET".equals(exchange.getRequestMethod())){
			LOGGER.info(PROCESSING_GET_METHOD);
			List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();
			Map<String, String> params = splitQuery(exchange.getRequestURI());
			Optional<String> Optionalnombre = getParameterValue(params, "name");
			String nombre = Optionalnombre.get();
			List<BankAccountDTO> accountsFiltered = bankAccountService.getAccountsLocal();
			accountsFiltered.clear();
			for (int i = 0; i < accounts.size(); i++) {
				if (accounts.get(i).getAccountName().indexOf(nombre) >= 0) {
					accountsFiltered.add(accounts.get(i));
					break;
				}
			}
			JSONArray json = new JSONArray(accountsFiltered);
			responseText = json.toString();
			exchange.getResponseHeaders().add("Content-type", "application/json");
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
		} else{
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();
		Instant finalDeEjecucion = Instant.now();
		LOGGER.info(CLOSING_RESOURCES);
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info(TIME_OF_RESPONSE.concat(total));
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static void getAccountByUser(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		Instant inicioDeEjecucion = Instant.now();
		BankAccountService bankAccountService = new BankAccountServiceImpl();
		String responseText = "";
		if (GET_METHOD.equals(exchange.getRequestMethod())) {
			LOGGER.info(PROCESSING_GET_METHOD);
			List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();
			List<BankAccountDTO> accountsFiltered = bankAccountService.getAccountsLocal();
			accountsFiltered.clear();
			Map<String, String> params = splitQuery(exchange.getRequestURI());
			Optional<Object> Optionaluser= getParameterValueObject(params, "user");
			Object user = Optionaluser.get();
			for (int i = 0; i < accounts.size(); i++) {
				if (accounts.get(i).getUserName().indexOf(user.toString()) >= 0) {
					accountsFiltered.add(accounts.get(i));
				}
			}
			JSONArray json = new JSONArray(accountsFiltered);
			responseText = json.toString();
			exchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TYPE_JSON);
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();
		Instant finalDeEjecucion = Instant.now();
		LOGGER.info(CLOSING_RESOURCES);
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info(TIME_OF_RESPONSE.concat(total));
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}


	private static void getAccountsGroupByType(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		Instant inicioDeEjecucion = Instant.now();
		BankAccountService bankAccountBO = new BankAccountServiceImpl();
		String responseText = "";
		if (GET_METHOD.equals(exchange.getRequestMethod())) {
			LOGGER.info(PROCESSING_GET_METHOD);
			List<BankAccountDTO> accounts = bankAccountBO.getAccountsLocal();

			Map<String, List<BankAccountDTO>> groupedAccounts;
			Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
			groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));

			JSONObject json = new JSONObject(groupedAccounts);
			responseText = json.toString();
			exchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TYPE_JSON);
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE, RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();
		Instant finalDeEjecucion = Instant.now();

		LOGGER.info(CLOSING_RESOURCES);
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
		LOGGER.info(TIME_OF_RESPONSE.concat(total));
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static void getEncryptedAccounts(HttpExchange exchange) throws IOException{
		LOGGER.info(INIT_PROCESS);
		Instant inicioDeEjecucion = Instant.now();
		BankAccountService bankAccountService = new BankAccountServiceImpl();
		String responseText = "";
		if (GET_METHOD.equals(exchange.getRequestMethod())) {
			LOGGER.info(PROCESSING_GET_METHOD);
			List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();

			byte[] keyBytes = new byte[]{
					0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
			};
			byte[] ivBytes = new byte[]{
					0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
			};
			Security.addProvider(new BouncyCastleProvider());

			SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
				cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
				// Cifraremos solamente el nombre y el country (pueden cifrar todos los parámetros que gusten)
				for (int i = 0; i < accounts.size(); i++) {
					String accountName = accounts.get(i).getAccountName();
					byte[] arrAccountName = accountName.getBytes();
					byte [] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
					int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
					ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
					accounts.get(i).setAccountName(accountNameCipher.toString());

					String accountCountry = accounts.get(i).getCountry();
					byte[] arrAccountCountry = accountCountry.getBytes();
					byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
					int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
					ctAccountNameLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
					accounts.get(i).setCountry(accountCountryCipher.toString());

				}
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			} catch (NoSuchProviderException e) {
				throw new RuntimeException(e);
			} catch (NoSuchPaddingException e) {
				throw new RuntimeException(e);
			} catch (InvalidAlgorithmParameterException e) {
				throw new RuntimeException(e);
			} catch (ShortBufferException e) {
				throw new RuntimeException(e);
			} catch (IllegalBlockSizeException e) {
				throw new RuntimeException(e);
			} catch (BadPaddingException e) {
				throw new RuntimeException(e);
			} catch (InvalidKeyException e) {
				throw new RuntimeException(e);
			}
			JSONArray json = new JSONArray(accounts);
			responseText = json.toString();
			exchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TYPE_JSON);
			exchange.sendResponseHeaders(METHOD_OK_CODE, responseText.getBytes().length);
		} else {
			exchange.sendResponseHeaders(METHOD_NOT_ALLOWED_CODE,RESPONSE_LENGTH);
		}
		OutputStream output = exchange.getResponseBody();
		Instant finalDeEjecucion = Instant.now();

		LOGGER.info(CLOSING_RESOURCES);
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info(TIME_OF_RESPONSE.concat(total));
		output.write(responseText.getBytes());
		output.flush();
		output.close();
		exchange.close();
	}

	private static Optional<Object> getParameterValueObject(Map<String, String> param, String paramName) {
		String val = param.get(paramName);
		if (val != null && val != "") {
			return Optional.ofNullable(val);
		}
		return Optional.ofNullable("NA");
	}

	private static Optional<String> getParameterValue(Map<String, String> param, String paramName) {
		String val = param.get(paramName);
		if (val != null && val != "") {
			return Optional.ofNullable(val);
		}
		return Optional.ofNullable("NA");
	}

	@Override
	public void run() {
		try {
			crearUsuarios();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new ExcepcionGenerica(e.getMessage());
		}
	}

	private void crearUsuarios(){
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
				Thread.sleep(SLEEP_MILLIS);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @deprecated(since = "Anotaciones update")
	 */
	@Deprecated(since = "Anotaciones update")
	private void createUsers() {
		try {
			String user = "user";
			String pass = "password";
			JSONArray jsonArray = new JSONArray(textThread);
			JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
			JSONObject user2 = new JSONObject(jsonArray.get(1).toString());
			JSONObject user3 = new JSONObject(jsonArray.get(2).toString());

			ResponseDTO response = createUser(user1.getString(user), user1.getString(pass));
			responseTextThread = new JSONObject(response).toString();
			LOGGER.info("Usuario 1: " + responseTextThread);
			Thread.sleep(SLEEP_MILLIS);

			response = createUser(user2.getString(user), user2.getString(pass));
			responseTextThread = new JSONObject(response).toString();
			LOGGER.info("Usuario 2: " + responseTextThread);
			Thread.sleep(SLEEP_MILLIS);

			response = createUser(user3.getString(user), user3.getString(pass));
			responseTextThread = new JSONObject(response).toString();
			LOGGER.info("Usuario 3: " + responseTextThread);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static BankAccountDTO getAccountDetails(String user, String lastUsage) {
		BankAccountService bankAccountService = new BankAccountServiceImpl();
		return bankAccountService.getAccountDetails(user, lastUsage);
	}

	private static ResponseDTO login(String user, String password) {
		UserService userService = new UserServiceImpl();
		return userService.getUserMongo(user, password);
	}

	private static ResponseDTO createUser(String user, String password) {
		UserService userService = new UserServiceImpl();
		UserDTO userDTO = new UserDTO(user,password);
		return userService.createUserMongo(userDTO);
	}

	public static Map<String, String> splitQuery(URI uri) {
		//Revisión: Uso de por lo menos un mapa
		Map<String, String> queryPairs = new LinkedHashMap<>();
		String query = uri.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
					URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
		}
		return queryPairs;
	}

}
