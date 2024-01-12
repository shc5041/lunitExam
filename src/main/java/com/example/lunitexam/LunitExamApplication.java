package com.example.lunitexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LunitExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunitExamApplication.class, args);
    }
}
