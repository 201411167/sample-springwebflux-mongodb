package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/webflux")
    public Mono<String> webfluxTest() throws InterruptedException {
        Thread.sleep(2000);
        return Mono.just("hello world");
    }

    @GetMapping("/web")
    public String webTest() throws InterruptedException {
        Thread.sleep(2000);
        return "hello world";
    }
}
