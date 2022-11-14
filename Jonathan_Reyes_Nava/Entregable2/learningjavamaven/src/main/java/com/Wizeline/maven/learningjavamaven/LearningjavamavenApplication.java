package com.Wizeline.maven.learningjavamaven;

import com.Wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.Wizeline.maven.learningjavamaven.service.UserService;
import com.Wizeline.maven.learningjavamaven.service.UserServiceImpl;
import com.Wizeline.maven.learningjavamaven.utils.exceptions.ExceptionGenerica;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Logger;

@SpringBootApplication
public class LearningjavamavenApplication extends Thread {
	private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());
	private static String SUCCESS_CODE = "OK000";
	private static String responseTextThread = "";
	private ResponseDTO response;
	private static String textThread = "";

	//@Autowired
	//private EndpointBean endpointBean;

	//@Bean
	//public static UserService userService(){
	//	return new UserServiceImpl();
	//}

	@Override
	public void run(){
		try {
			crearUsuarios();
		}
		catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new ExceptionGenerica(e.getMessage());   }
	}

	private void crearUsuarios() {
		try {
			String user = "user";
			String pass = "password";
			JSONArray jsonArray = new JSONArray(textThread);
			JSONObject userJson;      ResponseDTO response = null;
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
		UserService userBo = new UserServiceImpl();
		return userBo.createUser(User, password);
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavamavenApplication.class, args);

		LOGGER.info("LearningJava - Iniciado servicio REST ...");
	}
	//	HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
	//	String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

		//LOGEAR USUARIO
	//	server.createContext("/api/login", (exchange ->{
	//		LOGGER.info("msgProcPeticion");
	//		ResponseDTO response =new ResponseDTO();
	//		String responseText = "";
	//		/** Validates the type of http request */
	//		if ("GET".equals(exchange.getRequestMethod())) {
	//			LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
	//			UserDTO user = new UserDTO();
	//			user = user.getParameters(splitQuery(exchange.getRequestURI()));
	//			response = login(user.getUser(), user.getPassword());
	//			JSONObject json = new JSONObject(response);
	//			responseText = json.toString();
	//			exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
	//			exchange.sendResponseHeaders(200,responseText.getBytes().length);
	//		} else {
	//			/** 405 Method Not allowed */
	//			exchange.sendResponseHeaders(405,-1);
	//		}
	//		OutputStream output = exchange.getResponseBody();



	//		LOGGER.info("LearningJava - Cerrando recursos ...");
	//		output.write(responseText.getBytes());
	//		output.flush();
	//		output.close();
	//		exchange.close();
	//	}));

		//CREAR USUARIO
	//	server.createContext("/api/createUser", (exchange -> {
	//		LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
	//		ResponseDTO response = new ResponseDTO();
	//		String responseText = "";
	//		/** Validates the type of http request */
	//		exchange.getRequestBody();
	//		if ("POST".equals(exchange.getRequestMethod())) {

	//			StringBuilder text = new StringBuilder();
	//			try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
	//				while (scanner.hasNext()) {
	//					text.append(scanner.next());
	//				}
	//			} catch (Exception e) {
	//				LOGGER.severe(e.getMessage());
	//				throw new ExceptionGenerica("Fallo al obtener el request del body");
	//			}
	//			LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Create user");

	//			ObjectMapper objectMapper = new ObjectMapper();
	//			UserDTO user = objectMapper.readValue(text.toString(), UserDTO.class);

//				response = createUser(user.getUser(), user.getPassword());
//				JSONObject json = new JSONObject(response);
//				responseText = json.toString();
//				exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				/** 405 Method Not Allowed */
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//			/**
//			 * Always remember to close the resources you open.
//			 * Avoid memory leaks
//			 */
//			LOGGER.info("LearningJava - Cerrando recursos ...");
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));

		//CREAR USUARIOS
//		server.createContext("/api/createUsers", (exchange -> {
//			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
//			ResponseDTO response = new ResponseDTO();
//			/** Validates the type of http request */
//			exchange.getRequestBody();

//			if ("POST".equals(exchange.getRequestMethod())) {
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST");
//
//				StringBuilder text = new StringBuilder();
//				try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
//					while(scanner.hasNext()) {
//						text.append(scanner.next());
//					}
//				} catch (Exception e) {
//					LOGGER.severe(e.getMessage());
//					throw new ExceptionGenerica("Fallo al obtener el request del body");
//				}
//				textThread = text.toString();
//
//				LOGGER.info(textThread);
//				LearningjavamavenApplication thread = new LearningjavamavenApplication();
//				thread.start();
//
//				while(thread.isAlive());
//

//				exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
//				exchange.sendResponseHeaders(200,responseTextThread.getBytes().length);
//			} else {
//				/** 405 Method Not Allowed */
//				exchange.sendResponseHeaders(405,-1);
//			}
//			OutputStream output = exchange.getResponseBody();
//
//			LOGGER.info("LearningJava - Cerrando recursos ...");
//			output.write(responseTextThread.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));
//
		//Consultar información de cuenta de un usuario.
//		server.createContext("/api/getUserAccount", (exchange -> {
//			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
//			Instant inicioDeEjecucion = Instant.now();
//			ResponseDTO response = new ResponseDTO();
//
//			String responseText = "";
//			/** Validates the type of http request */
//			if ("GET".equals(exchange.getRequestMethod())) {
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
//				UserDTO user = new UserDTO();
//				Map<String, String> params = splitQuery(exchange.getRequestURI());
//				user = user.getParameters(params);
//				// Valida formato del parametro fecha
//				String lastUsage = params.get("date");
//				if (isDateFormatValid(lastUsage)) {
					//Valida el password del usuario
//					if (isPasswordValid(user.getPassword())) {
//						response = login(user.getUser(), user.getPassword());
//						if (response.getCode().equals(SUCCESS_CODE)) {
//							BankAccountDTO bankAccountDTO = getAccountDetails(user.getUser(), lastUsage);
//							JSONObject json = new JSONObject(bankAccountDTO);
//							responseText = json.toString();
//							exchange.getResponseHeaders().add("Content-type", "application/json");
//							exchange.sendResponseHeaders(200, responseText.getBytes().length);
//						}
//					} else {
//						responseText = "Password Incorrecto";
//						exchange.getResponseHeaders().add("Content-type", "application/json");
//						exchange.sendResponseHeaders(401, responseText.getBytes().length);
//					}
//				} else {
//					responseText = "Formato de Fecha Incorrecto";
//					exchange.getResponseHeaders().add("Content-type", "application/json");
//					exchange.sendResponseHeaders(400, responseText.getBytes().length);
//				}
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
		//Consultar información de todas las cuentas
//		server.createContext("/api/getAccounts", (exchange -> {
//			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
//			Instant inicioDeEjecucion = Instant.now();
//			BankAccountService bankAccountBO = new BankAccountServiceImpl();
//			String responseText = "";
//
//			/**Validate the type of http request */
//			if ("GET".equals(exchange.getRequestMethod())) {
//				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
//				List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
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

//			LOGGER.info("LearningJava - Cerrando recursos ...");
//			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
//			LOGGER.info("Tiempo de respuesta: ".concat(total));
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));

		//Consultar todas las cuentas y agruparlas por su tipo utilizando Programación Funcional
//		server.createContext("/api/getAccountsGroupByType", (exchange -> {
//			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
//			Instant inicioDeEjecucion = Instant.now();
//			BankAccountService bankAccountBO = new BankAccountServiceImpl() {
//			};
//			String responseText = "";
//			/** Validates the type of http request. */
//			if ("GET".equals(exchange.getRequestMethod())) {
//				LOGGER.info("LearningJava - Procesando petición HTPP de tipo GET");
//				List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
//
				//Aqui implementaremos la programación funcional
//				Map<String, List<BankAccountDTO>> groupedAccounts;
//				Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
//				groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));

//				JSONObject json = new JSONObject(groupedAccounts);
//				responseText = json.toString();
//				exchange.getResponseHeaders().add("Content-type", "application/json");
//				exchange.sendResponseHeaders(200, responseText.getBytes().length);
//			} else {
//				/** 405 Method Not Allowed */
//				exchange.sendResponseHeaders(405, -1);
//			}
//			OutputStream output = exchange.getResponseBody();
//			Instant finalDeEjecucion = Instant.now();

//			LOGGER.info("LearningJava - Cerrando recursos ...");
//			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat("segundos."));
//			LOGGER.info("Tiempo de respuesta: ".concat(total));
//			output.write(responseText.getBytes());
//			output.flush();
//			output.close();
//			exchange.close();
//		}));


//		/** Creates a default executor */
//		server.setExecutor(null);
//		server.start();
//		LOGGER.info("LearningJava - Server started on port 8080");
//	}

//	@Override
//	public void run() {
//		try {
//			crearUsuarios();
//		} catch (Exception e) {
//			LOGGER.severe(e.getMessage());
//			throw new ExceptionGenerica(e.getMessage());
//		}
//	}
//	private void crearUsuarios() {
//		try {
//			String user = "user";
//			String pass = "password";
//			JSONArray jsonArray = new JSONArray(textThread);
//			JSONObject userJson;
//			ResponseDTO response = null;

//			LOGGER.info("jsonArray.length(): " + jsonArray.length());
//			for (int i = 0; i < jsonArray.length(); i++) {
//				userJson = new JSONObject(jsonArray.get(i).toString());
//				response = createUser(userJson.getString(user), userJson.getString(pass));
//				responseTextThread = new JSONObject(response).toString();
//				LOGGER.info("Usuario " + (i + 1) + ": " + responseTextThread);
//				Thread.sleep(1000);
//			}
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//	}

//	@Deprecated(since = "Anotaciones update")
//			private void createUser() {
//		try {
//			String user = "user";
//			String pass ="password";
//			JSONArray jsonArray = new JSONArray(textThread);
//			JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
//			JSONObject user2 = new JSONObject(jsonArray.get(1).toString());
//			JSONObject user3 = new JSONObject(jsonArray.get(2).toString());

//			ResponseDTO response  = createUser(user1.getString(user), user1.getString(pass));
//			responseTextThread = new JSONObject(response).toString();
//			LOGGER.info("Usuario 1: " + responseTextThread);
//			Thread.sleep(1000);

//			response = createUser(user2.getString(user), user2.getString(pass));
//			responseTextThread = new JSONObject(response).toString();
//			LOGGER.info("Usuario 2: " + responseTextThread);
//			Thread.sleep(1000);

//			response = createUser(user3.getString(user), user3.getString(pass));
//			responseTextThread = new JSONObject(response).toString();
//			LOGGER.info("Usuario 3: " + responseTextThread);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//	}

//	private static ResponseDTO login(String User, String password) {
//		UserService UserService = userService();
//		return UserService.login(User, password);
//	}

//	private static ResponseDTO createUser(String User, String password) {
//		UserService UserService = userService();
//		return UserService.createUser(User, password);
//	}

//	public static Map<String, String> splitQuery(URI uri) {
//		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
//		String query = uri.getQuery();
//		String[] pairs = query.split("&");
//		for (String pair : pairs) {
//			int idx = pair.indexOf("=");
//			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
//					URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
//		}
//		return query_pairs;
//	}

//	private static BankAccountDTO getAccountDetails(String user, String lastUsage) {
//		BankAccountService bankAccountBO = new BankAccountServiceImpl();
//		return bankAccountBO.getAccountDetails(user, lastUsage);
//	}
}

