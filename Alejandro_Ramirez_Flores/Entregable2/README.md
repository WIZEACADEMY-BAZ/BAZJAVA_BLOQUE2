Nombre: Alejandro Ramirez Flores, Desarrollador Java
correo personal: aramirezflores589@gmail.com
correo empresa: alejandro.ramirezf@elektra.com.mx

.Uso de Api Optional: dentro de la clase BankingAccountNominaRepositoryImpl

.El archivo .gitignore se encuentra en la raiz del proyecto e ignora varios archivos, que pueden 
causar ruido al momento de revisarlo, entre ellos; .idea, .iws, .iml, .ipr, etc.

.Creacion de API; Deriva del paquete controller CuentaNomController (con los metodos, Get, Post, Put, Delete), la interface CuentaNomService y la clase CuentaNomServiceImpl.

Se expone la informacion del proyecto, mediante el endpoin de actuator (archivo properties).

.Los endpoints de actuator, atravez del archivo; application.yml

.Para el tema de cifrado se usan las clases: jwtTokenConfig, Authenticationcontroller, JwttokenFilter que extiende de OncePerRequestFilter, la clase securityConfig, los enndpoints se encuentran en la Clase BankingAccountController
y la Clase ControllerAdvice para manejar la exception AccesDeniedException(lanzada por la validacion del token).
 


