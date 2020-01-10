FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_HOST=localhost:3306
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_DATABASE=keep_note6
ENV MYSQL_URL=jdbc:mysql://${MYSQL_HOST}/${MYSQL_DATABASE}
ADD ./target/UserAuthentication-Service-0.0.1-SNAPSHOT.jar /usr/src/UserAuthentication-Service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","UserAuthentication-Service-0.0.1-SNAPSHOT.jar"]
