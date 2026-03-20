package com.ipze.ventilation_support_system.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//package com.ipze.ventilation_support_system.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RouteConfiguration {
//
//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder,
//                               AuthenticationPrefilter authFilter) {
//        return builder.routes()
//                // Auth-Service
//                .route("auth-service-route", r -> r.path("/auth-service/**")
//                        .filters(f -> f.rewritePath("/auth-service/(?<segment>.*)", "/auth/${segment}")
//                                .filter(authFilter.apply(new AuthenticationPrefilter.Config())))
//                        .uri("http://localhost:8080")) // auth-service на 8080
//                // User-Service
//                .route("user-service-route", r -> r.path("/user-service/**")
//                        .filters(f -> f.rewritePath("/user-service/(?<segment>.*)", "/user/${segment}")
//                                .filter(authFilter.apply(new AuthenticationPrefilter.Config())))
//                        .uri("http://localhost:8084")) // user-service на 8084
//                .build();
//    }
//}
@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,
                               AuthenticationPrefilter authFilter) {

        return builder.routes()

                .route("auth-service", r -> r
                        .path("/auth-service/**")
                        .filters(f -> f.rewritePath(
                                "/auth-service/(?<segment>.*)",
                                "/auth/${segment}"))
                        .uri("http://localhost:8080"))

                .route("user-service", r -> r
                        .path("/user-service/**")
                        .filters(f -> f
                                .rewritePath("/user-service/(?<segment>.*)",
                                        "/user/${segment}")
                                .filter(authFilter))
                        .uri("http://localhost:8084"))

                .build();
    }
}