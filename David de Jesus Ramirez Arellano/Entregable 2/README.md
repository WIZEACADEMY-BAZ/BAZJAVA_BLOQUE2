Forma de probar leaningjavagradle

Para probar las apis generadas en la coleccion que se anexa hay 3 carpetas

-API NOMINA (PROPIA) 
  esta conformado por 4 request uno de cada tipo (GET,POST,PUT,DELETE), son sobre una coleccion nueva llamada BankAccountNomina que hereda de BankAccountDTO, estas       operaciones igual se hacen en mongo con MongoTemplate

-Authenticate mis roles
  se cuentan con 2 roles para probar la api propia, el rol USUARIO es para los metodos GET,POST,PUT y el rol ADMINISTRADOR es usado para el metodo DELETE
  En esta carpeta hay 2 Request para generar el token de validacion de cada uno de los roles
  
-RestTemplate
Es la api para probar el rest template para consumo de una api publica, con la respuesta se genera un archivo .xml que se puede encontrar en la raiz del proyecto (usuario.xml)









