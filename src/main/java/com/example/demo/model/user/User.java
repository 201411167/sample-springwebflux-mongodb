package com.example.demo.model.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {
    @Id
    private String email;
    private String name;

    @Builder
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
