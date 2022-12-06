package com.wizeline;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.sun.net.httpserver.HttpServer;
import com.wizeline.gradle.learningjava.config.EndPointBean;
import com.wizeline.model.BankAccountDTO;
import com.wizeline.model.ResponseDTO;
import com.wizeline.model.UserDTO;
import com.wizeline.service.BankAccountService;
import com.wizeline.service.BankAccountServiceImpl;
import com.wizeline.service.UserService;
import com.wizeline.service.UserServiceImpl;
import com.wizeline.utils.Utils;
import com.wizeline.utils.exceptions.ExcepcionGenerica;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.function.Function;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class LearningJava extends Thread{
	
	private static final Logger log = Logger.getLogger(LearningJava.class.getName());
	private static final Object SUCCES_CODE = "OK000";
	private static int port = 8080;
	private static String responseTextThread = "";
	private ResponseDTO response;
	private static String textThread = "";
	private static final String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";
	
	@Autowired
	private EndPointBean endpointBean;
	
	@Bean
   	public static UserService userService() {
        	return new UserServiceImpl();
	}
	
	public static void inicio(String...args)throws IOException {
		log.info("LearningJava - Iniciando servicio Rest...");
		HttpServer server = HttpServer.create((new InetSocketAddress(port)), 0);
		
		server.createContext("/api/login", (exchange -> {
			log.info("LearningJava - Inicia procesamiento de peticion....");
			ResponseDTO response = new ResponseDTO();
			String responseText = "";
			if("GET".equals(exchange.getRequestMethod())) {
				log.info("LearningJava - Procesando peticion de tipo HTTP Get");
				UserDTO user = new UserDTO();
				user = user.getParameters(splitQuery(exchange.getRequestURI()));
				response = login(user.getUser(), user.getPassword());
				JSONObject json = new JSONObject(response);
				responseText = json.toString();
				exchange.getResponseHeaders().set("contentType","application/json; charset=UTF-8");
				exchange.sendResponseHeaders(200, responseText.getBytes().length);
			}else {
				exchange.sendResponseHeaders(405,-1);
			}
			OutputStream output = exchange.getResponseBody();
			log.info("LearningJava - Cerrando recursos... ");
			output.write(responseText.getBytes());
			output.flush();
			output.close();
			exchange.close();
		}));
		
		
		
		server.createContext("/api/getAccountsGroupByType", (exchange -> {
	        log.info(msgProcPeticion);
	        Instant inicioDeEjecucion = Instant.now();
	        BankAccountService bankAccountBO = new BankAccountServiceImpl();
	        String responseText = "";
	        
	        if ("GET".equals(exchange.getRequestMethod())) {
	            log.info("LearningJava - Procesando peticion HTTP GET");
	            List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
	            
	            Map<String, List<BankAccountDTO>> groupedAccounts;
	            Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
	            groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
	            JSONObject json = new JSONObject(groupedAccounts);

	            JSONArray jsonAccounts = new JSONArray(accounts);
	            responseText = jsonAccounts.toString();
	            exchange.getResponseHeaders().add("Content-type", "application/json");
	            exchange.sendResponseHeaders(200, responseText.getBytes().length);
	        } else {
	            
	            exchange.sendResponseHeaders(405, -1);
	        }
	        OutputStream output = exchange.getResponseBody();
	        Instant finalDeEjecucion = Instant.now();
	        
	        log.info("LearningJava - Cerrando recursos ...");
	        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
	        log.info("Tiempo de respuesta: ".concat(total));
	        output.write(responseText.getBytes());
	        output.flush();
	        output.close();
	        exchange.close();
	    }));
		
		
		server.createContext("/api/createUser", (exchange -> {
			log.info("LearningJava - Inicia procesamiento de peticion....");
			ResponseDTO response = new ResponseDTO();
			String responseText = "";
			if("POST".equals(exchange.getRequestMethod())) {
				log.info("LearningJava - Procesando peticion de tipo HTTP POST");
				UserDTO user = new UserDTO();
				user = user.getParameters(splitQuery(exchange.getRequestURI()));
				response = createUser(user.getUser(), user.getPassword());
				JSONObject json = new JSONObject(response);
				responseText = json.toString();
				exchange.getResponseHeaders().set("contentType","application/json; charset=UTF-8");
				exchange.sendResponseHeaders(200, responseText.getBytes().length);
			}else {
				exchange.sendResponseHeaders(405,-1);
			}
			OutputStream output = exchange.getResponseBody();
			log.info("LearningJava - Cerrando recursos... ");
			output.write(responseText.getBytes());
			output.flush();
			output.close();
			exchange.close();
		}));
		
		
		server.createContext("/api/createUsers", (exchange -> {
			log.info("LearningJava - Inicia procesamiento de peticion....");
			ResponseDTO response = new ResponseDTO();
			exchange.getRequestBody();
			
			if("POST".equals(exchange.getRequestMethod())) {
				log.info("LearningJava - Procesando peticion de tipo HTTP POST");
				StringBuilder text = new StringBuilder();
				try (Scanner scanner = new Scanner(exchange.getRequestBody())){
					while(scanner.hasNext()) {
						text.append(scanner.next());
					}
				}catch (Exception e) {
					log.severe(e.getMessage());
					throw new ExcepcionGenerica("Fallo al intentar obtener el request del body");
				}
				textThread = text.toString();
				log.info(textThread);
				
				LearningJava thread  = new LearningJava();
				thread.start();
				
				while(thread.isAlive());
				exchange.getResponseHeaders().set("contentType","application/json; charset=UTF-8");
				exchange.sendResponseHeaders(200, responseTextThread.getBytes().length);
			}else {
				exchange.sendResponseHeaders(405,-1);
			}
			OutputStream output = exchange.getResponseBody();
			log.info("LearningJava - Cerrando recursos... ");
			output.write(responseTextThread.getBytes());
			output.flush();
			output.close();
			exchange.close();
		}));
		
		
		server.createContext("/api/getAccounts", (exchange -> {
			log.info("LearningJava - Inicia procesamiento de peticion GETAccounts....");
			BankAccountService bankAccountBO = new BankAccountServiceImpl();
			String responseText = "";
			if("GET".equals(exchange.getRequestMethod())) {
				log.info("LearningJava - Procesando peticion de tipo HTTP GET");
				List<BankAccountDTO> accounts = bankAccountBO.getAccounts();

				JSONObject json = new JSONObject(accounts);
				responseText = json.toString();
				exchange.getResponseHeaders().set("contentType","application/json");
				exchange.sendResponseHeaders(200, responseText.getBytes().length);
			}else {
				exchange.sendResponseHeaders(405,-1);
			}
			OutputStream output = exchange.getResponseBody();
			log.info("LearningJava - Cerrando recursos... ");
			output.write(responseText.getBytes());
			output.flush();
			output.close();
			exchange.close();
		}));
		
		
		
		
		
		server.createContext("/api/getAccountByName", (exchange -> {
			log.info(msgProcPeticion);
			Instant inicioDeEjecucion = Instant.now();
			BankAccountService bankAccountBO = new BankAccountServiceImpl();
			String responseText = "";
			
			if("GET".equals(exchange.getRequestMethod())) {
				log.info("LearningJava - Procesando peticion HTTP de tipo GET");
				List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
				//Aqui
				Map<String, String> params = splitQuery(exchange.getRequestURI());
				Optional<String> optionalNombre = getParameterValue(params, "name");
				String nombre = optionalNombre.get();
				List<BankAccountDTO> accountsFiltered = bankAccountBO.getAccounts();
				accountsFiltered.clear();
				for(int i=0; i<accounts.size(); i++) {
					if(accounts.get(i).getAccountName().indexOf(nombre) >= 0) {
						accountsFiltered.add(accounts.get(i));
						break;
					}
				}
				
				
				JSONArray json = new JSONArray(accountsFiltered);
				responseText = json.toString();
				exchange.getResponseHeaders().add("Content-type", "application/json");
				exchange.sendResponseHeaders(200, responseText.getBytes().length);
			}else {
				exchange.sendResponseHeaders(405,-1);
			}
			
			OutputStream output = exchange.getResponseBody();
			Instant finalDeEjecucion = Instant.now();
			
			log.info("LearningJava - Cerrando recursos ...");
			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat("milisegundos"));
			log.info("Tiempo de respuesta:".concat(total));
			output.write(responseText.getBytes());
			output.flush();
			output.close();
			exchange.close();
			
		}));
		
		
		
		
		server.createContext("/api/getAccountsByUser", (exchange -> {
	        log.info(msgProcPeticion);
	        Instant inicioDeEjecucion = Instant.now();
	        BankAccountService bankAccountBO = new BankAccountServiceImpl();
	        String responseText = "";
	        
	        if ("GET".equals(exchange.getRequestMethod())) {
	            log.info("LearningJava - Procesando peticion HTTP GET");
	            List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
	            List<BankAccountDTO> accountsFiltered = bankAccountBO.getAccounts();
	            accountsFiltered.clear();


	            Map<String, String> params = splitQuery(exchange.getRequestURI());
	            Optional<Object> Optionaluser = getParameterValueObject(params, "user");
	            Object user = Optionaluser.get();
	           for (int i = 0; i < accounts.size(); i++) {
	                if (accounts.get(i).getUser().indexOf(user.toString()) >= 0) {
	                    accountsFiltered.add(accounts.get(i));
	                }
	            }
	            JSONArray json = new JSONArray(accountsFiltered);
	            responseText = json.toString();
	            exchange.getResponseHeaders().add("Content-type", "application/json");
	            exchange.sendResponseHeaders(200, responseText.getBytes().length);
	        } else {
	            exchange.sendResponseHeaders(405, -1);
	        }
	        OutputStream output = exchange.getResponseBody();
	        Instant finalDeEjecucion = Instant.now();
	        
	        log.info("LearningJava - Cerrando recursos ...");
	        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
	        log.info("Tiempo de respuesta: ".concat(total));
	        output.write(responseText.getBytes());
	        output.flush();
	        output.close();
	        exchange.close();
	    }));
		
		
		
		server.createContext("/api/getEncryptedAccounts", (exchange -> {
            log.info(msgProcPeticion);
            Instant inicioDeEjecucion = Instant.now();
            BankAccountService bankAccountBO = new BankAccountServiceImpl();
            String responseText = "";
            
            if ("GET".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP GET");
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();

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
                exchange.getResponseHeaders().add("Content-type", "application/json");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();
        
        log.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        log.info("Tiempo de respuesta: ".concat(total));
        output.write(responseText.getBytes());
        output.flush();
        output.close();
        exchange.close();
    }));
		
		
		
		server.createContext("/api/getUserAccount", (exchange -> {
			log.info("LearningJava - Inicia procesamiento de peticion....");
			Instant inicioDeEjecucion = Instant.now();
			ResponseDTO response = new ResponseDTO();
			String responseText = "";
			if("GET".equals(exchange.getRequestMethod())) {
				log.info("LearningJava - Procesando peticion de tipo HTTP GET");
				UserDTO user = new UserDTO();
				Map<String, String> params = splitQuery(exchange.getRequestURI());
				user = user.getParameters(params);
				String lastUsage = params.get("date");
				if(Utils.isDateFormatValid(lastUsage)) {
					if(Utils.isPasswordValid(user.getPassword())) {
						response = login(user.getUser(), user.getPassword());
						if(response.getCodigo().equals(SUCCES_CODE)) {
							BankAccountDTO banckAccount = getAccountDetails(user.getUser(), lastUsage);
							JSONObject json = new JSONObject(banckAccount);
							responseText = json.toString();
							exchange.getResponseHeaders().set("contentType","application/json");
							exchange.sendResponseHeaders(200, responseText.getBytes().length);
						}
					}else {
						responseText = "Password incorrecto";
						exchange.getResponseHeaders().set("contentType","application/json");
						exchange.sendResponseHeaders(401, responseText.getBytes().length);
					}
				}else {
					responseText = "Formato de fecha incorrecto";
					exchange.getResponseHeaders().set("contentType","application/json");
					exchange.sendResponseHeaders(400, responseText.getBytes().length);
				}

				
								
			}else {
				exchange.sendResponseHeaders(405,-1);
			}
			
			OutputStream output = exchange.getResponseBody();
			Instant finalDeEjecucion = Instant.now();
			
			log.info("LearningJava - Cerrando recursos... ");
			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos"));
			log.info("Tiempo de respuesta : ".concat(total));
			output.write(responseText.getBytes());
			output.flush();
			output.close();
			exchange.close();
		}));
		
		server.setExecutor(null);
		server.start();
		log.info("LearningJava - Server started on port 8080");
		
	}
	
	
	private static Optional<Object> getParameterValueObject(Map<String, String> param, String paramName) {
		String val = param.get(paramName);
	     if (val != null && val != "") {
	         return Optional.ofNullable(val);
	     }
	     return Optional.ofNullable("NA");
	}


	private static Optional<String> getParameterValue(Map<String, String> params, String paramName) {
		String val = params.get(paramName);
		if(val != null && val != "") {
			return Optional.ofNullable(val);
		}
		return Optional.ofNullable("NA");
	}
	

	public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException{
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		String query = uri.getQuery();
		String[] pairs = query.split("&");
		for(String pair : pairs) {
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0,idx), "UTF-8"), URLDecoder.decode(pair.substring(idx+1), "UTF-8"));
		}
		return query_pairs;
	}
	
	private static ResponseDTO login(String user, String password) {
		UserService userBO = new UserServiceImpl();
		return userBO.login(user, password);
	}
	
	private static ResponseDTO createUser(String user, String password) {
		UserService userBO = new UserServiceImpl();
		return userBO.createUser(user, password);
	}
	
	private static BankAccountDTO getAccountDetails(String user, String lastUsage) {
		BankAccountService bankAccountBO = new BankAccountServiceImpl();
	    return bankAccountBO.getAccountDetails(user, lastUsage);
	}
	
	@Override
	public void run() {
		try {
			crearUsuarios();
		}catch(Exception e) {
			throw new ExcepcionGenerica("Excepcion generica: "+e.getMessage());
		}
	}
	
	private void crearUsuarios() {
		try {
			String user = "user";
			String pass = "password";
			JSONArray jsonArray =  new JSONArray(textThread);
			JSONObject userJson;
			
			ResponseDTO response = null;
			log.info("jsonArray.length() :{}" + jsonArray.length());
			
			for(int i = 0; i <jsonArray.length(); i++) {
				userJson = new JSONObject(jsonArray.get(i).toString());
				response = createUser(userJson.getString(user), userJson.getString(pass));
				responseTextThread = new JSONObject(response).toString();
				log.info("Usuario " + (i+1) + " : " + responseTextThread);
				Thread.sleep(1000);
			}
			
		}catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Deprecated(since="Anotacions update")
	public void createUsers() {
		try {
			String user = "user";
	        String pass = "password";
	        JSONArray jsonArray = new JSONArray(textThread);
	        JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
	        JSONObject user2 = new JSONObject(jsonArray.get(1).toString());
	        JSONObject user3 = new JSONObject(jsonArray.get(2).toString());
	        
	        response = createUser(user1.getString(user), user1.getString(pass));
	        responseTextThread = new JSONObject(response).toString();
	        log.info("Usuario 1: " + responseTextThread);
	        Thread.sleep(1000);
	        
	        response = createUser(user2.getString(user), user2.getString(pass));
	        responseTextThread = new JSONObject(response).toString();
	        log.info("Usuario 2: " + responseTextThread);
	        Thread.sleep(1000);
	        
	        response = createUser(user3.getString(user), user3.getString(pass));
	        responseTextThread = new JSONObject(response).toString();
	        log.info("Usuario 3: " + responseTextThread);			
		}catch (InterruptedException e) {
	         throw new RuntimeException(e);
	     }
	}

}
