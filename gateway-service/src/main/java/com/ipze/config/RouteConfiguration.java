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
                .route(createRoute("auth-service", "/auth-service", authFilter))
                .route(createRoute("chat-service", "/chat-service", authFilter))
                .route(createRoute("user-service", "/user-service", authFilter))
                .build();
    }

    private Function<PredicateSpec,Buildable<Route>>
    createRoute(String serviceName, String pathPrefix, GatewayFilter filter) {
        return r -> r.path(pathPrefix + "/**")
                .filters(f -> f
                        .rewritePath(pathPrefix + "/(?<segment>.*)", "/"
                                + serviceName.replace("-service", "") + "/${segment}")
                        .filter(filter))
                .uri("lb://" + serviceName);
    }
}