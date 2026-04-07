package com.ipze.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public record RabbitProperties (
    @Value("${spring.rabbitmq.host}")
    String relayHost,
    @Value("${spring.rabbitmq.port}")
    int port,
    @Value("${spring.rabbitmq.username}")
    String clientLogin,
    @Value("${spring.rabbitmq.password}")
    String password)
{}