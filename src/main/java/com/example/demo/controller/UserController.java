package com.example.demo.controller;

import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{email}")
    public Mono<User> findUserByEmail(@PathVariable String email) {
        return userRepository.findById(email);
    }

    @GetMapping("/all")
    public Flux<User> findAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/input")
    public Mono<User> inputUser(@RequestBody Map<String, Object> req){
        String email = (String) req.get("email");
        String name = (String) req.get("name");

        log.info("email: " + email);
        log.info("name: " + name);

        return userRepository.save(User.builder().email(email).name(name).build());
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody Map<String, Object> req){
        String email = (String) req.get("email");

        log.info("email: " + email);

        userRepository.deleteById(email).subscribe();
    }

    @PutMapping("/update")
    public Mono<User> updateName(@RequestBody Map<String, Object> req){
        String email = (String)req.get("email");
        String new_name = (String)req.get("name");
        return userRepository.findById(email).flatMap(user->{
            return userRepository.save(User.builder().email(email).name(new_name).build());
        });
    }
}
