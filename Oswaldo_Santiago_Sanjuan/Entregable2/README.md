## pruebas de endpoint
``` python
1. Tipos de datos
    http://localhost:8080/api/getUserAccount?user=oswaldo&password=Oswald1$&date=12-03-1912

2. Expresiones Regulares
   http://localhost:8080/api/getUserAccount?user=oswaldo&password=Oswald1$&date=12-03-1912

3. Colecciones
   http://localhost:8080/api/getAccounts

4. Fechas y Tiempos
   http://localhost:8080/api/getUserAccount?user=oswaldo&password=Oswald1$&date=12-03-1912

5. Concurrencia
  http://localhost:8080/api/createUsers

6. Excepciones
   http://localhost:8080/api/createUsers

7. Anotaciones
   http://localhost:8080/api/createUsers

8. optional
   localhost:8080/api/getAccountByName?name=Dummy Account

9. Genéricos
   localhost:8080/api/getAccountsByUser?user=user3@wizeline.com

10. Programación funcional
    localhost:8080/api/getAccountsGroupByType

11. Encriptación
    localhost:8080/api/getEncryptedAccounts

```

## Instalación de java 11

Descarga [sunn corretto java 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) instalación

``` java
versión de java en el archivo pom.xml se encuentra la versión de java.

	<properties>
		<java.version>11</java.version>
	</properties>
```
### Herencia
La herencia es uno de los conceptos más importantes de la programación orientada a objetos, y su principal ventaja es la capacidad de utilizar el mismo código que hemos programado en una clase, en otras clases que heredan de ella sus métodos (funciones) y sus atributos (variables).
```java
herencia
En la clase principal llamada 'BankAccountServiceImpl ' se realiza erencia.

@Service
public class BankAccountServiceImpl implements BankAccountService { También Se realiza herencia en la clase 'BankAccountServiceImpl'
      public class BankAccountServiceImpl implements BankAccountService
```
### Sobrecarga de al menos uno de los métodos de alguna clase

La sobrecarga de métodos nos permite crear varios métodos con el mismo nombre pero que puedan recibir diferentes parámetros.

```java
public class BankAccountServiceImpl implements BankAccountService {
    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7)));
        accountDTOList.add(buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2)));
        accountDTOList.add(buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4)));
        return accountDTOList;
    }

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(randomBalance());
        bankAccountDTO.setAccountType(pickRandomAccountType());
        bankAccountDTO.setCountry(getCountry(country));
        bankAccountDTO.getLastUsage(); // <- Se invoca al metodo de acceso get() para obtener la fecha actual
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
    //Sobrecarga recibiendo un solo parámetro 
    public BankAccountDTO getAccountDetails(String user) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(5);
        bankAccountDTO.setAccountName("Dummy Account ");
        return bankAccountDTO;
    }
}

```
### Sobrecarga de al menos uno de los constructores de alguna clase
nos permite  especial para crear e inicializar un objeto creado a partir de una clase.
```java
en la clase public class ErrorDTO tenemos la sobrecarga de un contructor 

public class ErrorDTO {

    String errorCode;

    String message;

    public ErrorDTO(ErrorDTOBuilder bilder) {
       this.errorCode = bilder.errorCode;
       this.errorCode = bilder.message;
    }

    public ErrorDTO() {
        super();
    }
}
```
### Encapsulamiento de al menos una clase
Son las clases, donde encapsulamos y englobamos tanto métodos como propiedades.
```java
en la clase class ResponseDTO se realiza el encapsulamiento.

public class ResponseDTO {
    private String status;
    private String code;
    private ErrorDTO errors = new ErrorDTO();
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public ErrorDTO getErrors() {
        return errors;
    }
    public void setErrors(ErrorDTO errors) {
        this.errors = errors;
    }
}
```
### Clase interna dentro de al menos una clase


```java
public class ErrorDTO {
    String errorCode;
    String message;
    
    public ErrorDTO(ErrorDTOBuilder bilder) {
       this.errorCode = bilder.errorCode;
       this.errorCode = bilder.message;
    }
    public ErrorDTO() {
        super();
    }
    public String getErrorCode() {
        return errorCode;
    }
    public String getMessage() {
        return message;
    }
    public static class ErrorDTOBuilder{
        private String errorCode;
        private String message;
        public ErrorDTOBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }
        public ErrorDTOBuilder message(String message){
            this.message = message;
            return this;
        }
        public ErrorDTO build(){
            return new ErrorDTO(this);
        }
    }
}

```




## Uso de por lo menos una interfaz de creación propia
```java
Se usando la interfaz public interface UserService.

public interface UserService {
    ResponseDTO createUser(String user, String password);
    ResponseDTO login(String user, String password);
}


```

### Uso de por lo menos una expresión regular
``` java
en la clase public class Utils se tiene una expresion regular.
  
private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
  private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

  // Definicion del patron para validar formato de fecha (dd-mm-yyyy)
  private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

```
### Uso de por lo menos un arreglo

```java

en la clase public class BankAccountServiceImpl implements BankAccountService se realiza una lista de arreglos.

    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();

```
## Uso de por lo menos un mapa
``` java
Se encuentra un método de tipo map en la clase UserController. 

   public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }

```

## Uso de por lo menos una lista
```java
en la clase public class BankAccountServiceImpl implements BankAccountService  se encuentra una lista 

    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);
        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountOne);
        BankAccountDTO bankAccountTwo = buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);
        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountTwo);
        BankAccountDTO bankAccountThree = buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);
        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountThree);
        //Imprime en la Consola cuales son los records encontrados en la coleccion
        //bankAccountCollection de la mongo db
        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUserName()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection " + user );
                });
        //Esta es la respuesta que se retorna al Controlador
        //y que sera desplegada cuando se haga la llamada a los
        //REST endpoints que la invocan (un ejemplo es el endpoint de  getAccounts)
        return accountDTOList;
    }

```

## Uso de API de Fechas y Tiempos en un método
```java
en la clase BankAccountServiceImpl se tiene las fechas y tiempos.

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

```
### Implementación de concurrencia ejecutando hilos y utilizando el resultado generado.

```java
en la clase UserController se tiene la implementación de concurrencia.
    public void run() {
        try {
            crearUsuarios();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

```

### Uso de por lo menos una excepción de creación propia.

```java
En la clase java ExcepcionGenerica se tiene excepciones propias.

public class ExcepcionGenerica extends RuntimeException {
    public ExcepcionGenerica(String mensajeError) {
        super(mensajeError);
    }
}

```
### Uso de al menos tres anotaciones
```java
se tiene una anotación en la clase UserController.
1. @RestController

2. @RequestMapping("/api")

3. @Autowired

```
### Uso de por lo menos 1 Optional
```java



```

### Uso de por lo menos 1 tipos de dato genérico
```java 
    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password){
        LOGGER.info("Entrando a realizar la peticion ");
        ResponseDTO response = new ResponseDTO();

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        UserDTO userName = new UserDTO();
        StringBuilder builder = new StringBuilder("http://localhost:8080/api/login");
        builder.append("?user=" + user);
        builder.append("&password=" + password);
        URI uri = URI.create(builder.toString());
        userName = userName.getParameters(splitQuery(uri));
        response = commonServices.login(userName.getUser(), userName.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<ResponseDTO>(response, responseHeaders, HttpStatus.OK);
    }

```

### Uso de por lo menos 1 Interfaz Funcional
```java 
Se esta usando la interfaz 
public interface UserRepository {
}
```

### Uso de por lo menos 1 función Lambda y asignada en la clase UserController
```java
   @PostMapping("/createUsers")
    public ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info("Prepararando para crear los usaurios...... ");
        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
        List<ResponseDTO> responseList = new ArrayList<>();

        userDTOList.stream().forEach(userDTO -> {
                    String user = userDTO.getUser();
                    String password = userDTO.getPassword();
                    response.set(createUser(user, password));
                    responseList.add(response.get());
                }
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<List<ResponseDTO>>(responseList, responseHeaders, HttpStatus.OK);
    }
```


### Uso de por lo menos 1 Stream de datos

```java
    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);
        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountOne);
        BankAccountDTO bankAccountTwo = buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);
        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountTwo);
        BankAccountDTO bankAccountThree = buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);
        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountThree);
        //Imprime en la Consola cuales son los records encontrados en la coleccion
        //bankAccountCollection de la mongo db
        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUserName()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection " + user );
                });
        //Esta es la respuesta que se retorna al Controlador
        //y que sera desplegada cuando se haga la llamada a los
        //REST endpoints que la invocan (un ejemplo es el endpoint de  getAccounts)
        return accountDTOList;
    }

} 

```

### Uso de por lo menos 2 operaciones intermedias y 2 tipos de colectores en un Stream.

```java
1. en la clase UserController tenemos una operacion Stream.
    @PostMapping("/createUsers")
    public ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info("Prepararando para crear los usaurios...... ");
        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
        List<ResponseDTO> responseList = new ArrayList<>();

        userDTOList.stream().forEach(userDTO -> {
                    String user = userDTO.getUser();
                    String password = userDTO.getPassword();
                    response.set(createUser(user, password));
                    responseList.add(response.get());
                }
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<List<ResponseDTO>>(responseList, responseHeaders, HttpStatus.OK);
    }

2.     public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }


```

### Uso de por lo menos 1 algoritmo de cifrado
```java
En la clase UserController Inicializamos las llaves y establecemos el algoritmo "DES" de cifrado.

SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
Cipher cipher = null;
```
### Uso de por lo menos 1 proveedor de cifrado

```java

En la clase UserController se tiene o Inicializamos el Provider de Security y establecemos que sera BouncyCastle.

Security.addProvider(new BouncyCastleProvider());
```


### Uso de por lo menos 1 funcionalidad de cifrado

```java
en la clase UserController Recorremos todas las cuentas y ciframos tanto el nombre como el país de la misma (Se pueden encriptar todos los campos de ser requerido).

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
```

### Se agrego una foto de 
Spring_Initializr.jpg

## Archivo desplegable usando Gradle o Maven

``` java
el proyecto se encuentra desplegado en Maven.
  
```
