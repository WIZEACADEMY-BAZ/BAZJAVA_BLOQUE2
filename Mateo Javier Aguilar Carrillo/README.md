<center><h1> En construcción</center>

# Directorio Entregas de Mateo Javier Aguilar Carrillo

A continuación se describe la estructura de las carpetas y evidencias, incluyendo las instrucciones, de los programas y entregas de Mateo Javier Aguilar Carrillo

## Entregable 1

    > Curso GIT
        - Screeshots del curso
    > Curso MicroServicios
        - Archivos ejecutables Docker
    > Curso RestApi
        - Screenshots de evidencias y Postman Collection
    > Curso Kafka
        - Screenshots de evidencias
    > Curso MongoDB
        - Screenshots de evidencias y export de la BD


## Entregable 2

    > Curso Spring
        - Proyecto Java 11 con Gradle y screenshot de creacion de proyecto

## Instrucciones para Docker

### 1. Descargar los archivos y colocarlos en una carpeta </br>
### 2. Abrir la Terminal o PowerShell dentro de la carpeta </br>
### 3. Ejecutar el siguiente script para construir la imagen: </br>

    
    docker build --tag python-docker .

### 4. Se instancia un contenedor de Docker con la imagen creada, exponiendo el puerto: </br>

    docker run --publish 8080:5000 python-docker

### 5. Se prueba el contenedor

    curl --request GET --url http://localhost:8080/
