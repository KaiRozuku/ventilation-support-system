package com.ipze.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.rabbitmq")
public record RabbitProperties(
        String host,
        int port,
        String username,
        String password
) {}