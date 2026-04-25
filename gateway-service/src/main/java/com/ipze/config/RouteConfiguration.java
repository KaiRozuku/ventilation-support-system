package com.ipze.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,
                               GatewayFilter authFilter) {

        return builder.routes()
                .route(createRoute("auth-services", "/auth-services", authFilter))
                .route(createRoute("chat-services", "/chat-services", authFilter))
                .route(createRoute("user-services", "/user-services", authFilter))
                .route("chat-ws", r -> r
                        .path("/ws/**")
                        .uri("lb:ws://chat-services"))
                .build();
    }

    private Function<PredicateSpec,Buildable<Route>>
    createRoute(String serviceName, String pathPrefix, GatewayFilter filter) {
        return r -> r.path(pathPrefix + "/**")
                .filters(f -> f
                        .rewritePath(pathPrefix + "/(?<segment>.*)", "/"
                                + serviceName.replace("-services", "") + "/${segment}")
                        .filter(filter))
                .uri("lb://" + serviceName);
    }
}