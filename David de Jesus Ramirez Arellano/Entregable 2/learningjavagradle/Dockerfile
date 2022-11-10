FROM openjdk:11

LABEL maintainer=CursoWizelineDavid
COPY build/libs/learningjavagradle-0.0.1-SNAPSHOT.jar /lib/learningjavagradle-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENV AMBIENTE=local
ENV LANG=C.utf8
ENV TZ=America/Mexico_City
USER root
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=$AMBIENTE -jar /lib/learningjavagradle-0.0.1-SNAPSHOT.jar"]