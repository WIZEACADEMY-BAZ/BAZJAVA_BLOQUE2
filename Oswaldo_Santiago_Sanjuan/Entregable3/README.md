# Entregable 3

#### Implementación de un Job con Spring Batch usando un Reader, un Processor y un Writer.


``` java

Se tiene la clase BankAccountJob en el paquete batch y se está usando Spring Batch usando un Reader, un Processor y un Writer.



@Configuration
public class BankAccountJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job bankAccountsBackupJob() {
        return jobBuilderFactory.get("bankAccountsBackupJob")
                .start(bankAccountsBackupStep(stepBuilderFactory))
                .listener(jobExecutionListener())
                .build();
    }
    @Bean
    public Step bankAccountsBackupStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("bankAccountsBackupStep")
                .<BankAccountDTO, String>chunk(5).reader(bankAccountsReader())
                .processor(bankAccountItemProcessor()).writer(bankAccountsWriter()).build();
    }
    @Bean
    public FlatFileItemReader<BankAccountDTO> bankAccountsReader() {
        return new FlatFileItemReaderBuilder<BankAccountDTO>()
                .name("bankAccountsReader")
                .resource(new ClassPathResource("csv/accounts.csv"))
                .delimited().names(new String[] {"country", "accountName", "accountType", "accountBalance", "userName"})
                .targetType(BankAccountDTO.class).build();
    }
    @Bean
    public FlatFileItemWriter<String> bankAccountsWriter() {
        return new FlatFileItemWriterBuilder<String>()
                .name("bankAccountsWriter")
                .resource(new FileSystemResource(
                        "target/test-outputs/bankAccountsBackup.txt"))
                .lineAggregator(new PassThroughLineAggregator<>()).build();
    }
    @Bean
    public BankAccountItemProcessor bankAccountItemProcessor() {
        return new BankAccountItemProcessor();
    }
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new BatchJobCompletionListener();
    }
}

```
### Uso de Dockerfile para desplegar la aplicación en un contenedor

```bash
Se tiene un archivo desde la raíz del proyecto y que se llame Dockerfile.

FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Creación de contrato de API usando Swagger (OpenAPI) en un controlador.

``` java
en el paquete controller se tiene la clase BankingAccountController en donde se esta usando un controlador.

    @GetMapping("/getExternalUser/{userId}")
    public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {

        Post postTest = accountsJSONClient.getPostById(userId);
        LOGGER.info("Getting post userId..." +postTest.getUserId());
        LOGGER.info("Getting post body..." +postTest.getBody());
        LOGGER.info("Getting post title..." +postTest.getTitle());
        postTest.setUserId("External user "+randomAcountNumber());
        postTest.setBody("No info in accountBalance since it is an external user");
        postTest.setTitle("No info in title since it is an external user");
        LOGGER.info("Setting post userId..." +postTest.getUserId());
        LOGGER.info("Setting post body..." +postTest.getBody());
        LOGGER.info("Setting post title...."+postTest.getTitle());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
    }
```

### Creación de un consumidor de Kafka	

``` java
Se tiene un paquete consumer y adentro del paquete se tiene una clase llamada KafkaConsumer 

@Component
public class KafkaConsumer {
    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
        System.out.println("Received: " + account.getUserName());
    }
```

#### Creación de un productor de Kafka	
``` java
En el paquete configuration se tiene una clase KafkaConfiguration
```

#### Creación de un productor de Kafka	
``` java
En el paquete configuration se tiene una clase KafkaConfiguration.
```

Configuración de la comunicación entre productor y consumidor de Kafka	
``` java
En el paquete configuration se tiene una clase KafkaConfiguration.
``` 

Implementación de un patrón de diseño de creación.
``` java
En el paquete controller de la clase BankingAccountController se tiene llamando el enpoid @GetMapping("/factory") se implementó el patrón de factory.

en el paquete de model de tiene la fábrica que se llama AccountFactory y sus clases Nominating savin y platium.

``` 

### Implementación de un patrón de diseño de comportamiento.

``` java
El patrón de diseño de comportamiento que se implemento fue  chainofresponsibility  en el paquete controller de la clase BankingAccountController el empoid que se esta consuminedo se llama @GetMapping("/responsibility") y se tine un paquete llamado chainofresponsibility en donde realiso todo el desarrollo.
 
``` 

### Implementación del patrón de diseño "Throttling"
``` java
en el paquete controller se tiene la clase UserController en donde se está implementado el patrón con el enpoind que se esta consuminedo     @GetMapping("/users")
``` 

```

