package com.ipze.service;

import com.ipze.dto.UserShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient webClient;


    public List<UserShortDto> getUsers(List<String> userIds) {

        String token = SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials()
                .toString();

        return webClient.post()
                .uri("/auth/management/users/batch")
                .header("Authorization", token)
                .bodyValue(userIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserShortDto>>() {})
                .block();
    }
}