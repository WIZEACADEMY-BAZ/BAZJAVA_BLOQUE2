Neri Sanchez Lozano.

Entregable Semana 3 y 4. 

Spring initilzr : PrintScreen de proyecto base

RestTemplate: consumo de servicio rest de tipo GET de dominio público.
	- Clase: PublicApi.class
	- api: /api/restTemplateConsume
	- Tipo: GET

Interface propia: ActivityService

CRUD Spring MVC:
	GET: /getAccountByUser
	POST: 
	PUT:
	DELETE:

Spring security:
	- Recursos abiertos: 
			("/api/authenticate").permitAll()
            ("/api/restTemplateConsume").permitAll()
            ("/api/sayHello").permitAll()
            ("/swagger-ui/**").permitAll()
            ("/v3/api-docs/**").permitAll()
            ("/health").permitAll()
            ("/info").permitAll()
            ("/actuator/**").permitAll() 