FROM openjdk:11
ARG CLASS_FILE=build/libs/*.jar
COPY ${CLASS_FILE} "app.jar"
EXPOSE 8080
#ENTRYPOINT ["java","ProyectoApplication"]
ENTRYPOINT ["java","-jar","/app.jar"]