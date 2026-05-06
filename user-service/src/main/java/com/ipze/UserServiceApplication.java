package com.ipze;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSecurity
public class UserServiceApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("EUREKA_URL", dotenv.get("EUREKA_URL"));
        System.setProperty("AMQP", dotenv.get("AMQP"));
        System.setProperty("RABBIT_QUEUE_REC", dotenv.get("RABBIT_QUEUE_REC"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));

        SpringApplication.run(UserServiceApplication.class, args);
    }
}