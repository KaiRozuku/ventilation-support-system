package com.ipze;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("GIT_URL", dotenv.get("GIT_URL"));
        System.setProperty("GIT_USER", dotenv.get("GIT_USER"));
        System.setProperty("GIT_TOKEN", dotenv.get("GIT_TOKEN"));

        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
