import com.sun.net.httpserver.HttpServer;
import com.wizeline.BO.BankAccountBO;
import com.wizeline.BO.BankAccountBOImpl;
import com.wizeline.BO.UserBO;
import com.wizeline.BO.UserBOImpl;
import com.wizeline.DTO.BankAccountDTO;
import com.wizeline.DTO.ResponseDTO;
import com.wizeline.DTO.UserDTO;
import com.wizeline.utils.Utils;
import com.wizeline.utils.exceptions.ExcepcionGenerica;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;

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

public class LearningJava extends Thread {
    private static final Logger LOGGER = Logger.getLogger(LearningJava.class.getName());

    private static String responseTextThread = "";
    private ResponseDTO response;
    private static String textThread = "";

    public static void main(String[] args) throws IOException {
        LOGGER.info("LearningJava - Iniciando servicio REST ...");

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/login", (exchange -> {
            LOGGER.info("LearningJava - Iniciar procesamiento de peticion - login");
            ResponseDTO response = new ResponseDTO();
            String responseText= "";
            if("GET".equals(exchange.getRequestMethod())){
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                UserDTO user = new UserDTO();
                user = user.getParameters(splitQuery(exchange.getRequestURI()));
                response = login(user.getUser(), user.getPassword());
                JSONObject json = new JSONObject(response);
                responseText = json.toString();
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            }else{
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream outputStream = exchange.getResponseBody();
            LOGGER.info("Learning - Cerrando recursos ...");
            outputStream.write(responseText.getBytes());
            outputStream.flush();
            outputStream.close();
            exchange.close();
        }));

        /*
        server.createContext("/api/createUser", (exchange -> {
            LOGGER.info("LearningJava - Iniciar procesamiento de peticion - createUser");
            ResponseDTO response = new ResponseDTO();
            String responseText= "";
            if("POST".equals(exchange.getRequestMethod())){
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST");
                UserDTO user = new UserDTO();
                user = user.getParameters(splitQuery(exchange.getRequestURI()));
                response = createUser(user.getUser(), user.getPassword());
                JSONObject json = new JSONObject(response);
                responseText = json.toString();

                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            }else{
                exchange.sendResponseHeaders(405, -1);
            }

            OutputStream outputStream = exchange.getResponseBody();
            LOGGER.info("Learning - Cerrando recursos ...");
            outputStream.write(responseText.getBytes());
            outputStream.flush();
            outputStream.close();
            exchange.close();
        }));
        */

        // Expreciones regulares and Data-time
        server.createContext("/api/getUserAccount", (exchange -> {
            LOGGER.info("LearningJava - Iniciar procesamiento de peticion - getUserAccount");
            Instant inicioDeEjecucion = Instant.now(); // se crea una intancia con el tiempo actual que inicia
            ResponseDTO response = new ResponseDTO();
            String responseText= "";

            if("GET".equals(exchange.getRequestMethod())){
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                UserDTO user = new UserDTO();
                // expreciones regulares
                Map<String, String> params = splitQuery(exchange.getRequestURI());
                user = user.getParameters(params);
                String lastUsage = params.get("date");
                // se validan los datos proporcionados
                if(Utils.isDateFormatValid(lastUsage)){
                    if(Utils.isPasswordValid(user.getPassword())){
                        response = login(user.getUser(), user.getPassword());
                        if(response.getStatus().equals("success")){
                            BankAccountDTO bankAccountDTO = getAccountDetails(user.getUser(), lastUsage);
                            JSONObject json = new JSONObject(bankAccountDTO);
                            responseText = json.toString();
                            exchange.sendResponseHeaders(200, responseText.getBytes().length);
                        }
                    }else{
                        responseText = "Password incorrecta";
                        exchange.sendResponseHeaders(401, responseText.getBytes().length);
                    }
                }else{
                    responseText = "Formato de fecha incorrecto";
                    exchange.sendResponseHeaders(400, responseText.getBytes().length);
                }
            }else{
                exchange.sendResponseHeaders(405, -1);
            }

            OutputStream outputStream = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();
            LOGGER.info("Learning - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            LOGGER.info("tiempo de respuesta: ".concat(total));
            outputStream.write(responseText.getBytes());
            outputStream.flush();
            outputStream.close();
            exchange.close();
        }));

        // Estrucutras de datos
        server.createContext("/api/getAccounts", (exchange -> {
            LOGGER.info("LearningJava - Iniciar procesamiento de peticion - getAccounts");
            BankAccountBO bankAccountBO = new BankAccountBOImpl();
            String responseText= "";

            if("GET".equals(exchange.getRequestMethod())){
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
                JSONObject json = new JSONObject(accounts);
                responseText = json.toString();
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            }else{
                exchange.sendResponseHeaders(405, -1);
            }

            OutputStream outputStream = exchange.getResponseBody();
            LOGGER.info("Learning - Cerrando recursos ...");
            outputStream.write(responseText.getBytes());
            outputStream.flush();
            outputStream.close();
            exchange.close();
        }));

        // concurrencia
        server.createContext("/api/createUsers", (exchange -> {
            LOGGER.info("LearningJava - Inicia procesamiento de peticion - createUsers_thread ...");
            ResponseDTO response = new ResponseDTO();
            exchange.getRequestBody();
            if ("POST".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST");
                // Obtenemos el request del body que mandamos
                StringBuilder text = new StringBuilder();
                try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
                    while(scanner.hasNext()) {
                        text.append(scanner.next());
                    }
                }catch (Exception e) {
                    LOGGER.severe(e.getMessage());
                    throw new ExcepcionGenerica("Fallo al obtener el request del body");
                }
                textThread = text.toString();
                LOGGER.info(textThread);
                // Iniciamos thread
                LearningJava thread = new LearningJava();
                thread.start();
                // Esperamos a que termine el thread
                while(thread.isAlive());
                exchange.sendResponseHeaders(200, responseTextThread.getBytes().length);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            LOGGER.info("LearningJava - Cerrando recursos ...");
            output.write(responseTextThread.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        // Opcionales
        server.createContext("/api/getAccountByName", (exchange -> {
            LOGGER.info("LearningJava - Inicia procesamiento de peticion - getAccountByName ...");
            Instant inicioDeEjecucion = Instant.now();
            BankAccountBO bankAccountBO = new BankAccountBOImpl();
            String responseText = "";

            /** Validates the type of http request  */
            if ("GET".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
                // Aquí implementaremos nuestro código de filtrar las cuentas por nombre utilizando optional
                Map<String, String> params = splitQuery(exchange.getRequestURI());
                Optional<String> Optionalnombre = getParameterValue(params, "name");
                String nombre = Optionalnombre.get();
                List<BankAccountDTO> accountsFiltered = bankAccountBO.getAccounts();
                accountsFiltered.clear();
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountName().indexOf(nombre) >= 0) {
                        accountsFiltered.add(accounts.get(i));
                        break;
                    }
                }
                JSONArray json = new JSONArray(accountsFiltered);
                responseText = json.toString();
                //exchange.getResponseHeaders().add("Content-type", "application/json");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
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

        // Genericos
        server.createContext("/api/getAccountsByUser", (exchange -> {
            LOGGER.info("LearningJava - Inicia procesamiento de peticion - getAccountsByUser ...");
            Instant inicioDeEjecucion = Instant.now();
            BankAccountBO bankAccountBO = new BankAccountBOImpl();
            String responseText = "";
            /** Validates the type of http request  */
            if ("GET".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
                List<BankAccountDTO> accountsFiltered = bankAccountBO.getAccounts();
                accountsFiltered.clear();

                // Aquí implementaremos nuestro código de filtrar las cuentas por usuario usando genericos
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
                /** 405 Method Not Allowed */
                exchange.sendResponseHeaders(405, -1);
            }

            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();

            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            LOGGER.info("Tiempo de respuesta: ".concat(total));
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        // Programacion funcional
        server.createContext("/api/getAccountsGroupByType", (exchange -> {
            LOGGER.info("LearningJava - Inicia procesamiento de peticion - getAccountsByUser ...");
            Instant inicioDeEjecucion = Instant.now();
            BankAccountBO bankAccountBO = new BankAccountBOImpl();
            String responseText = "";
            if ("GET".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
                // Aqui implementaremos la programación funcional
                Map<String, List<BankAccountDTO>> groupedAccounts;
                Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
                groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));

                JSONArray json = new JSONArray(groupedAccounts);
                responseText = json.toString();
                exchange.getResponseHeaders().add("Content-type", "application/json");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                /** 405 Method Not Allowed */
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();

            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            LOGGER.info("Tiempo de respuesta: ".concat(total));
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        // Cifrado
        server.createContext("/api/getEncryptedAccounts", (exchange -> {
            LOGGER.info("LearningJava - Inicia procesamiento de peticion - getAccountsByUser ...");
            Instant inicioDeEjecucion = Instant.now();
            BankAccountBO bankAccountBO = new BankAccountBOImpl();
            String responseText = "";
            if ("GET".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();

                // Aquí implementaremos nuestro código de cifrar nuestras cuentas y regresarselas al usuario de manera cifrada
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
                exchange.getResponseHeaders().add("Content-type", "application/json");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();

            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            LOGGER.info("Tiempo de respuesta: ".concat(total));
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        // inicio del servidor de java
        server.setExecutor(null);
        server.start();
        LOGGER.info("LearningJava - Server started on port 8080");
    }

    public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for(String pair : pairs){
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    private static ResponseDTO createUser(String user, String password){
        UserBO userBO = new UserBOImpl();
        return userBO.createUser(user, password);
    }

    private static ResponseDTO login(String user, String password){
        UserBO userBO = new UserBOImpl();
        return userBO.login(user, password);
    }

    /*
    private static BankAccountDTO getAccountDetails(String user) {
        BankAccountBO bankAccountBO = new BankAccountBOImpl();
        return bankAccountBO.getAccountDetails(user);
    }
    */

    private static BankAccountDTO getAccountDetails(String user, String lastUsage) {
        BankAccountBO bankAccountBO = new BankAccountBOImpl();
        return bankAccountBO.getAccountDetails(user, lastUsage);
    }

    // anotaciones
    @Deprecated()
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
            Thread.sleep(1000);

            response = createUser(user2.getString(user), user2.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            LOGGER.info("Usuario 2: " + responseTextThread);
            Thread.sleep(1000);

            response = createUser(user3.getString(user), user3.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            LOGGER.info("Usuario 3: " + responseTextThread);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void crearUsuarios(){
        try{
            String user = "user";
            String password = "password";
            JSONArray jsonArray = new JSONArray((textThread));
            JSONObject userJson;

            ResponseDTO responseDTO = null;

            LOGGER.info("jsonArray.length(): " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++){
                userJson = new JSONObject(jsonArray.get(i).toString());
                responseDTO = createUser(userJson.getString(user), userJson.getString(password));
                responseTextThread = new JSONObject(responseDTO).toString();
                LOGGER.info("Usuario " + (i+1) + ": " + responseTextThread);
                Thread.sleep(1000);
            }

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    // concurrencia
    @Override
    public void run(){
        try {
            crearUsuarios();
            // codigo deprecated
            /*
            String user = "user";
            String pass = "password";
            JSONArray jsonArray = new JSONArray(textThread);
            JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
            JSONObject user2 = new JSONObject(jsonArray.get(1).toString());
            JSONObject user3 = new JSONObject(jsonArray.get(2).toString());

            ResponseDTO response = createUser(user1.getString(user), user1.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            LOGGER.info("Usuario 1: " + responseTextThread);
            Thread.sleep(1000);

            response = createUser(user2.getString(user), user2.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            LOGGER.info("Usuario 2: " + responseTextThread);
            Thread.sleep(1000);

            response = createUser(user3.getString(user), user3.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            LOGGER.info("Usuario 3: " + responseTextThread);
             */
        } catch (Exception e){
            //} catch (InterruptedException e) {
            // throw new RuntimeException(e);
            LOGGER.severe(e.getMessage());
            throw new ExcepcionGenerica(e.getMessage());
        }

    }

    // opcionales y genericos -> String to Object::class
    private static Optional<String> getParameterValue(Map<String, String> param, String paramName) {
        String val = param.get(paramName);
        if (val != null && val != "") {
            return Optional.ofNullable(val);
        }
        return Optional.ofNullable("NA");
    }

    // Genericos
    private static Optional<Object> getParameterValueObject(Map<String, String> param, String paramName) {
        String val = param.get(paramName);
        if (val != null && val != "") {
            return Optional.ofNullable(val);
        }
        return Optional.ofNullable("NA");
    }


}