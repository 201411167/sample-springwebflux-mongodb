package com.example.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * RANDOM_PORT
 * 이 경우 MockMvc 대신 RestTemplate를 사용할 수 있음
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest {

    private final CountDownLatch count = new CountDownLatch(10);

    @Before
    public void setup() {
        System.setProperty("reactor.netty.ioWorkerCount", "1");
    }

    @Test
    public void webTest() {
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("http://localhost:8080/api/test/web", String.class);
        assertThat(forObject, is("hello world"));
    }

    @Test
    public void webfluxTest() {
        WebClient webClient = WebClient.builder().build();
        Mono<ClientResponse> exchange = webClient.get().uri("http://localhost:8080/api/test/webflux").exchange();
        exchange.subscribe(clientResponse -> {
            Mono<String> stringMono = clientResponse.bodyToMono(String.class);
            stringMono.subscribe(s -> {
                assertThat(s, is("hello world"));
            });
        });
    }

    @Test
    public void blockingTest() {
        RestTemplate restTemplate = new RestTemplate();

        StopWatch watch = new StopWatch();
        watch.start();

        for (int i = 0; i < 10; i++) {
            String forObject = restTemplate.getForObject("http://localhost:8080/api/test/web", String.class);
            assertThat(forObject, is("hello world"));
        }

        watch.stop();
        System.out.println(watch.getTotalTimeSeconds());
    }

    @Test
    public void nonblockingTest() throws InterruptedException {
        WebClient webClient = WebClient.builder().build();

        StopWatch watch = new StopWatch();
        watch.start();

        for (int i = 0; i < 10; i++) {
            webClient.get()
                    .uri("http://localhost:8080/api/test/webflux")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            it -> {
                                count.countDown();
                                System.out.println(it);
                            }
                    );
        }
        count.await(10, TimeUnit.SECONDS);
        watch.stop();
        System.out.println(watch.getTotalTimeSeconds());
    }
}