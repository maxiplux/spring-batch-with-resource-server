package com.example.spring4resourceserver;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Spring4resourceserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring4resourceserverApplication.class, args);
    }

}
