# Entregables - Abraham gutierrez Lara
## Estructura de Entregable 1 (semana 1 y 2)

- GIT: capturas de pantalla de las operaciones de Git en GitBash
- Microservicios: archivos dockerFile y docker-compose para cargar una pequeña aplicación REST
- Rest APIs: colección de Postman para el consumo de APIs rest públicas utilizando los diferentes verbos HTTP
- Serverless: capturas de pantalla de la creación de una API en APIGateway conectado con una lambda en NodeJS montados sobre la plataforma de AWS
- Kafka: creación de un tópico con dos particiones y practica de Consumer y Producer
- MongoDB: capturas de pantalla de las operaciones Insert, UpdateMany y Find de mongo, igualmente se exportan colecciones con los datos

## Estructura de Entregable 2 y 3 (semana 3-6)

El entregable 2 representa una API centrada en simular dos recursos: cuentas de usuarios y cuentas bancarias asociadas a los usuarios.

Dado el contexto anterior se crearon los recursos necesarios para realizar las siguientes operaciones:
- Crear usuarios de tipo administradores (BANKER) y de tipo usuario
- Crear una cuenta bancaria asociada a un usuario existente
- Reportar operaciones fallidas mediante un flujo automatizado de excepciones y componentes que la atrapan (AOP) y reportan el error a Kafka; Ej: FailedLoginException
- Consumo de recursos externos mediante RestTemplate (Dummy)
- Job de Spring Batch encargado de leer las operaciones fallidas reportadas en el topico de Fallos: KafkaItemReader y KafkaItemWritter
- Autenticar al usuario mediante JWT y validar su acceso a recursos mediante roles de acceso
- Documentación mediante Swagger
- Conexión real a MongoDB para todas las operaciones utilizando MongoRepository y MongoTemplate
- Uso de hilos para operaciones en segundo plano e inyección de dependencias dentro del mismo mediante el SpringContext
- Manejo automático de excepciones mediante @ExceptionHanlder
- Validación de datos de entrada utilizando anotaciones de Javax

Como parte de las pruebas unitarias y de integración del proyecto se realizó:
- Cobertura del 70% de pruebas en conjunto del reporte de Jacoco
- Mock de componentes en pruebas unitarias (Mockito) y mock en integración con pruebas de SpringBootTest (MokcBean)
- Mock de los roles del usuario para "saltar" la verificación de roles del Spring Security
- Uso de una BD embebida para ambiente de test y sustituir el uso de Mongo
- Se deshabilitan componentes de Kafka en ambiente dev con @Profile("!test")
