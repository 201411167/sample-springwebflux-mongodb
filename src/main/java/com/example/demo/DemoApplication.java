package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableReactiveMongoRepositories
@RestController
public class DemoApplication {


    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.addListeners((ApplicationStartedEvent event) -> System.out.println("====== Demo Started ======"));
        app.run(args);

        String property = System.getProperty("reactor.netty.ioWorkerCount");
        System.out.println(property);
    }
}
