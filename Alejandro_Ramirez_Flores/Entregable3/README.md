Nombre: Alejandro Ramirez Flores, Desarrollador Java, Entregable3

.Implementación de Job con Spring Batch:
Se implemento usando las siguientes clases y paquetes,
Dentro del paquete batch; mediante la clase BankAccountJob.java, 
Carpeta csv dentro de carpeta resources; archivo accounts.csv,
Dentro del paquete configuration; clase de configuracion BatchConfiguration.java,
Salida de ejecucion de la aplicacion, en consola y en el archivo; bankAccountsBackup.txt (Se encuentra en el directorio; target/test-outputs),
dentro del paquete bach; clase UserJob.java, UserReader.java, UserWriter.java, UserProcesor.java, 
dentro del paquete controller; clase BatchController,
habilitar lo necesario para que el job no se ejecute al momento de iniciar la aplicacion, habilitar la consola;
ademas, habilitar las dependencias necesarias; dentro del archivo (archivo build.gradle).

.Integraciones:
Implementacion de Feign Client, Contenedor de aplicacion Sprigboot, Manejo de Ribbon, Implementacion de Kafka.
agregar las siguientes dependencias; spring-cloud-dependencies 2021.0.3, spring-cloud-starter-openfeign (archivo build.gradle),
interfaz dentro del paquete client AccountsJSONClient.java,
clase Post dentro del paquete model, endpoint getExternalUser de la clase BankingAccountController (dentro del paquete Controller),
modificar el endpoint getAccounts(), dentro del paquete paquete controller,
configuracion del archivo; application.properties,
////ademas, habilitar las dependencias necesarias;
Correr la aplicacion LoadBalancer que se encuentra en el directorio; BAZJAVA_BLOQUE2\Alejandro_Ramirez_Flores\Entregable3\LoadBalancer,
clases KafkaConsumer.java, KafkaConfiguration.java, KafkaConsumer.java.
usar el archivo; application.properties para el manejo de puertos en diferentes instancias,

configuracion del archivo Dockerfile en la raiz del proyecto, 
habilitar los request necesarios en el archivo postman;
getExternalUser/1


.Patron Creacional (Singleton):
Se utilizo el paquete singleton (archivo Cliente.java), paquete UserController.java, metodo getCliente. 

.Patron de diseño comportamiento:
Archivos; Channel.java, ChannelCollection.java, ChannelCollectionImpl.java, Client.java, Iterator.java e IteratorImpl.java(dentro el paquete iterator).
Ejercicio n. 1 propio se usa archivos: Producto.java (paquete iterator) y ProductosService.java(paquete Service). 
Ejercicio n. 2 propio se usa archivos: BankingAccountController (implementar el metodo fabrica), dentro del paquete model, las clases; AccountFabrica.java,
Nomina.java, Plata.java, ahorro.java, Interfaz Account.java(dentro del mismo paquete).

Patron de estructura:
agregar la dependencia; com.github.vladimir-bukhtoyarov,
clase UserController dentro del paquete Controller, completar el endpoint getUsers(), dentro de la misma clase.
 







