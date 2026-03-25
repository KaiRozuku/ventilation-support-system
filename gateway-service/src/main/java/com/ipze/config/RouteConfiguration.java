package com.ipze.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,
                               GatewayFilter authFilter) {

        return builder.routes()

                .route("auth-service", r -> r
                        .path("/auth-service/**")
                        .filters(f -> f.rewritePath(
                                "/auth-service/(?<segment>.*)",
                                "/auth/${segment}"))
                        .uri("http://localhost:8080"))
//                        .uri("lb://auth-service"))

                .route("user-service", r -> r
                        .path("/user-service/**")
                        .filters(f -> f
                                .rewritePath("/user-service/(?<segment>.*)", "/user/${segment}")
                                .filter(authFilter))
                        .uri("http://localhost:8084"))
//                        .uri("lb://user-service"))

                .build();
    }
}