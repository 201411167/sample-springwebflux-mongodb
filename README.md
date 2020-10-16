# Sample Spring Webflux - Reactive MongoDB, Docker

## [ API Reference ]

| URL                 | METHOD | 기능                                |
| ------------------- | ------ | ----------------------------------- |
| `/api/user/{email}` | GET    | DB에 저장된 사용자 정보 조회        |
| `/api/user/all`     | GET    | DB에 저장된 모든 사용자의 정보 조회 |
| `/api/user/input`   | POST   | 사용자의 정보를 DB에 저장           |
| `/api/user/delete`  | DELETE | 사용자의 정보를 DB에서 삭제         |
| `/api/user/update`  | PUT    | 사용자의 정보를 갱신                |



## [ Dependency ]

- Spring Webflux

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
  </dependency>
  ```

- Reactive MongoDB

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb-react</artifactId>
  </dependency>
  ```



## [ application.yml ]

```yaml
spring:
	data:
		mongodb:
			uri: mongodb://localhost:27017/test
```



## [ Dockerfile ]

```dockerfile
FROM openjdk
ARG JAR_FILE=/target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} demo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/demo.jar"]
```



## [ docker-compose.yml ]

```yaml
version: "3"
services:
  mongodb:
    image: mongo
    container_name: "mongodb"
    environment:
      - MONGO_DATA_DIR=/data/db
    ports:
      - 27017:27017
    command:
      - mongod
  app:
    image: app
    container_name: "app"
    environment:
      - spring.data.mongodb.uri=mongodb://mongodb:27017/test
    ports:
      - 8080:8080
    depends_on:
      - mongodb
    links:
      - mongodb
```

- app.environment
  - `spring.data.mongodb.uri` 등 설정을 오버라이딩 할 수 있음
- depends_on: 해당 컨테이너가 먼저 생성되고 실행됨
- links: 다른 서비스에 접근할 수 있음