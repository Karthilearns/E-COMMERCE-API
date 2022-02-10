package com.example.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan("com.example.ecom")
//@EnableJpaRepositories("com.example.ecom.repository")
//@EntityScan("com.example.ecom.model")
@SpringBootApplication
public class EComApplication {
    public static void main(String[] args) {
        SpringApplication.run(EComApplication.class, args);
    }

}
