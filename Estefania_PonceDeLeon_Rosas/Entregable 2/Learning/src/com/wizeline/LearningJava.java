package com.wizeline;

import com.sun.net.httpserver.HttpServer;
import com.wizeline.BO.BankAccountBO;
import com.wizeline.BO.BankAccountBOImpl;
import com.wizeline.BO.UserBO;
import com.wizeline.BO.UserBOImpl;
import com.wizeline.DTO.BankAccountDTO;
import com.wizeline.DTO.ResponseDTO;
import com.wizeline.DTO.UserDTO;
import org.json.JSONArray;
import org.json.JSONObject;

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
import java.util.logging.Logger;

import com.wizeline.utils.Utils;

public class LearningJava {

    private static final Logger LOGGER = Logger.getLogger(LearningJava.class.getName());
    private static String SUCCESS_CODE = "OK000";

    /*public static void main(String[] args) {
        System.out.println("Hello world! I am your Java friend");
    }*/

   public static void main(String[] args) throws IOException {
        LOGGER.info("LearningJava - Iniciado servicio REST ...");

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/login", (exchange -> {
            LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
            ResponseDTO response = new ResponseDTO();
            String responseText = "";

            if ("GET".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                UserDTO user = new UserDTO();
                Map<String, String> params = splitQuery(exchange.getRequestURI());
                user = user.getParameters(params);
                // Valida formato del parametro fecha (date) [dd-mm-yyyy]
                String lastUsage = params.get("date");
                if(Utils.isDateFormatValid(lastUsage)) {
                    // Valida el password del usuario (password)
                    if(Utils.isPasswordValid(user.getPassword())){
                        response = login(user.getUser(), user.getPassword());
                        if(response.getCode().equals(SUCCESS_CODE)){
                            BankAccountDTO bankAccountDTO = getAccountDetails(user.getUser(),lastUsage);
                            JSONObject json = new JSONObject(bankAccountDTO);
                            responseText = json.toString();
                            exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                            exchange.sendResponseHeaders(200, responseText.getBytes().length);
                        }
                    }else {
                        responseText = "Password incorrecto";
                        exchange.getResponseHeaders().add("Content-type","application/json");
                        exchange.sendResponseHeaders(401, responseText.getBytes().length);
                    }
                }

            } else {

                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();


            LOGGER.info("LearningJava - Cerrando recursos ...");
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));
        server.createContext("/api/createUser", (exchange -> {
            LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
            ResponseDTO response = new ResponseDTO();
            String responseText = "";

            exchange.getRequestBody();
            if ("POST".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                UserDTO user = new UserDTO();
                user = user.getParameters(splitQuery(exchange.getRequestURI()));
                response = createUser(user.getUser(), user.getPassword());
                JSONObject json = new JSONObject(response);
                responseText = json.toString();
                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);

            } else {

                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();

            LOGGER.info("LearningJava - Cerrando recursos ...");
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

       // Consultar información de cuenta de un usuario
       server.createContext("/api/getUserAccount", (exchange -> {
           LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
           Instant inicioDeEjecucion = Instant.now();
           ResponseDTO response = new ResponseDTO();

           String responseText = "";
           /** Validates the type of http request  */
           if ("GET".equals(exchange.getRequestMethod())) {
               LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
               UserDTO user =  new UserDTO();
               Map<String, String> params = splitQuery(exchange.getRequestURI());
               user = user.getParameters(params);
               // Valida formato del parametro fecha (date) [dd-mm-yyyy]
               String lastUsage = params.get("date");
               if (Utils.isDateFormatValid(lastUsage)) {
                   // Valida el password del usuario (password)
                   if (Utils.isPasswordValid(user.getPassword())) {
                       response = login(user.getUser(), user.getPassword());
                       if (response.getCode().equals(SUCCESS_CODE)) {
                           BankAccountDTO bankAccountDTO = getAccountDetails(user.getUser(), lastUsage);
                           JSONObject json = new JSONObject(bankAccountDTO);
                           responseText = json.toString();
                           exchange.getResponseHeaders().add("Content-type", "application/json");
                           exchange.sendResponseHeaders(200, responseText.getBytes().length);
                       }
                   } else {
                       responseText = "Password Incorrecto";
                       exchange.getResponseHeaders().add("Content-type", "application/json");
                       exchange.sendResponseHeaders(401, responseText.getBytes().length);
                   }
               } else {
                   responseText = "Formato de Fecha Incorrecto";
                   exchange.getResponseHeaders().add("Content-type", "application/json");
                   exchange.sendResponseHeaders(400, responseText.getBytes().length);
               }
           } else {
               /** 405 Method Not Allowed */
               exchange.sendResponseHeaders(405, -1);
           }
           OutputStream output = exchange.getResponseBody();
           Instant finalDeEjecucion = Instant.now();
           /**
            * Always remember to close the resources you open.
            * Avoid memory leaks
            */
           LOGGER.info("LearningJava - Cerrando recursos ...");
           String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
           LOGGER.info("Tiempo de respuesta: ".concat(total));
           output.write(responseText.getBytes());
           output.flush();
           output.close();
           exchange.close();
       }));

       // Consultar información de todas las cuentas
       server.createContext("/api/getAccounts", (exchange -> {
           LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
           BankAccountBO bankAccountBO = new BankAccountBOImpl();

           String responseText = "";
           /** Validates the type of http request  */
           if ("GET".equals(exchange.getRequestMethod())) {
               LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
               List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
               JSONArray json = new JSONArray(accounts);
               responseText = json.toString();
               exchange.getResponseHeaders().add("Content-type", "application/json");
               exchange.sendResponseHeaders(200, responseText.getBytes().length);
           } else {
               /** 405 Method Not Allowed */
               exchange.sendResponseHeaders(405, -1);
           }
           OutputStream output = exchange.getResponseBody();
           /**
            * Always remember to close the resources you open.
            * Avoid memory leaks
            */
           LOGGER.info("LearningJava - Cerrando recursos ...");
           output.write(responseText.getBytes());
           output.flush();
           output.close();
           exchange.close();
       }));

        server.setExecutor(null);
        server.start();
        LOGGER.info("LearningJava - Server started on port 8080");
    }

    private static ResponseDTO login(String User, String password) {
        UserBO userBo = new UserBOImpl();
        return userBo.login(User, password);
    }

    private static ResponseDTO createUser(String User, String password) {
        UserBO userBo = new UserBOImpl();
        return userBo.createUser(User, password);
    }

    public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {

        Map<String, String> query_pairs = new LinkedHashMap<String, String>();

        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        LOGGER.info(query_pairs.toString());
        return query_pairs;
    }

    private static BankAccountDTO getAccountDetails(String user, String lastUsage) {
        BankAccountBO bankAccountBO = new BankAccountBOImpl();
        return bankAccountBO.getAccountDetails(user, lastUsage);
    }
}