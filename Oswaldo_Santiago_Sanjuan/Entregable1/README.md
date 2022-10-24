# Entregable 1
Cursos: 
       GIT, 
       Microservicios, 
       Kafka,
       Monogo DB,
       Rest APIS,
       Servlets.

# Git
   Creacion de un Branch 

   Git add

   Git branch
   
   Git commit
   
   Git Push

# Microservicios
```bash
     ---> Crear el archivo de aplicación app.py
           from flask import Flask
           app = Flask(__name__)
           @app.route('/')
           def hello():
               return 'Hello World! I have been seen'

     ----> Definimos el archivo de requerimientos
           Dentro de requirements.txt agregamos:
           flash
           redis
    
       ----> Definimos el archivo Dockerfile y le agregamos lo siguiente
       # syntax=docker/dockerfile:1
             import time
             import redis
             from flask import Flask

            app = Flask(__name__)
            cache = redis.Redis(host='redis', port=6379)

            def get_hit_count():
                retries = 5
                while True:
                   try:
                     return cache.incr('hits')
                  except redis.exceptions.ConnectionError as exc:
                  if retries == 0:
                     raise exc
                  retries -= 1
                  time.sleep(0.5)

              @app.route('/')
              def hello():
              count = get_hit_count()
              return 'Hello World! I have been seen {} times.\n'.format(count)



     1. Construimos la imagen en docker
         docker build --tag python-docker .
     2. Ejecutamos el contenedor
       docker run --rm -d -p 8080:5000 --name topico python-docker
     
```

#  RestAPIs

``` python

   # POST
   # GET
   # PUT
   # DELETE
   # PATCH
```
# Serverless 
```
# Modelo de programación en lambda
# Cloudwatch
# Secrets manager
# Pricing
```

# Kafka 
``` python
      # Paso 1: Iniciar ZooKeeper service y Kafka Server
         .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
      # Luego abrimos una segunda terminal, y nos movemos de nuevo al directorio de kafka
         .\bin\windows\kafka-server-start.bat .\config\server.properties

      # Paso 2 :Crear un TOPIC para guardar los eventos
          .\bin\windows\kafka-topics.bat --create --topic <topic-name> --bootstrap-server localhost:<kafka-server-port>
      #Crear particiones de un un TOPIC
          bin/kafka-topics.sh --create --topic <topic-name> --partitions <#_particiones> --bootstrap-server localhost:<kafka-server-port>

      # Paso 3:    
         .\bin\windows\kafka-console-producer.bat --topic <topic-name> --bootstrap-server localhost:<kafka-server-port> 
         > <data>
         > <data>
         > <data>
      
     # PASO 4: Leer los eventos del TOPIC
       .\bin\windows\kafka-console-consumer.bat --topic <topic-name> --bootstrap-server localhost:<kafka-server-port> 

```
# MongoDB
    [MongoDB](https://www.mongodb.com/nosql-explained/nosql-vs-sql)
```python
    # Operaciones CRUD 
       --> Consultar documentos
       --> Actualizar documentos
       --> Eliminar documentos
```