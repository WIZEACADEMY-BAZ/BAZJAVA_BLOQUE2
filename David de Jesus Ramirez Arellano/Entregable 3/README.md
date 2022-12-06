Entregable 3



Implementación de un Job con Spring Batch usando un Reader, un Processor y un Writer
	- las implementacion esta dentro de com.wizeline.gradle.learningjavagradle.batch
	- la prueba de iniciar batch esta dentro de /learningjavagradle/src/main/java/com/wizeline/gradle/learningjavagradle/controller/BatchController.java


Uso de Dockerfile para desplegar la aplicación en un contenedor
	- El DockerFile esta en la raiz del proyecto

Creación de contrato de API usando Swagger (OpenAPI) en un controlador
	- acceder a http://localhost:8080/swagger-ui/index.html#/ para probar swagger
	- se anexa .png con la prueba swagger

Creación de un consumidor de Kafka
	- El consumer esta en la siguiente ruta /learningjavagradle/src/main/java/com/wizeline/gradle/learningjavagradle/consumer/KafkaConsumer.java

Creación de un productor de Kafka
	- En el package com.wizeline.gradle.learningjavagradle.client esta la implementacion del consumer y dentro del controlador BankingAccountController se encuentra el envio /send/{userId}

Configuración de la comunicación entre productor y consumidor de Kafka
	-Se encuentra en la siguiente ruta /learningjavagradle/src/main/java/com/wizeline/gradle/learningjavagradle/config/KafkaConfiguration.java

Implementación de un patrón de diseño de creación
	Se hizo la implementacion de un singleton el cual esta en com.wizeline.gradle.learningjavagradle.patterns.singleton

Implementación de un patrón de diseño de comportamiento
	- Se hizo la implentacion de un Iterator en el package com.wizeline.gradle.learningjavagradle.patterns.iterator

Implementación del patrón de diseño "Throttling"
	-La implementacion de Throttling se encuentra en el controlador ThrottlingController

Prueba unitaria de cada endpoint de la API
	-La prueba de cada endpoint esta dentro del package com.wizeline.gradle.learningjavagradle.controller ahi se encuentra una clase test por cada controlador que se tiene

Prueba unitaria de cada operación CRUD
	- Las pruebas del CRUD se encuentran dentro de com.wizeline.gradle.learningjavagradle.service donde se implementa el crud con MongoTemplate

Uso de Mockito en cada prueba
	-Para cada una de las pruebas se hizo el uso de Mockito 

Generación de logs por prueba	
	-Todas la pruebas incluyen logs

Pruebas para Happy Path
	-En todas las pruebas se incluye el mejor de los casos

Pruebas para cada Edge Case
	-Se hizo la prueba para cada caso en las pruebas donde fue necesario, se marcan con el nombre mas Correcto e incorrecto, siendo Correcto el mejor de los casos e Incorrecto un edge Case

Implementación de JaCoCo o SonarCloud (mínimo 70% de cobertura)
	-Dentro del build.gradle se encuentra la configuración para el jacoco, se ejecuta desde la consola haciendole un gradle build al proyecto,
         se pueden ver los resultados en la ruta E:\Users\1059833\Desktop\Wizeline e3\BAZJAVA_BLOQUE2\David de Jesus Ramirez Arellano\Entregable 3\learningjavagradle\build\reports\jacoco\test\html

	- Se anexa .png con la prueba de que se cumplio mas del 70%
 




