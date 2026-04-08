package com.ipze;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ChatServiceApplication {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        System.setProperty("RABBIT_HOST", dotenv.get("RABBIT_HOST"));
        System.setProperty("RABBIT_PORT", dotenv.get("RABBIT_PORT"));
        System.setProperty("RABBIT_USERNAME", dotenv.get("RABBIT_USERNAME"));
        System.setProperty("RABBIT_PASSWORD", dotenv.get("RABBIT_PASSWORD"));
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));

        SpringApplication.run(ChatServiceApplication.class, args);
    }
}