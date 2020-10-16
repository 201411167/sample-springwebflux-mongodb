FROM openjdk

ARG JAR_FILE=/target/demo-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} demo.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/demo.jar"]