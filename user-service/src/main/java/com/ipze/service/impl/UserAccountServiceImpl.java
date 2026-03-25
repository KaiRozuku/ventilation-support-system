package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final WebClient webClient;

    private void sendPutRequest(Object request, String uri, String authHeader){
        webClient.put()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
    @Override
    public void changePassword(ChangePasswordRequest request, String authHeader) {
        sendPutRequest(request, "/auth-service/api/account/password", authHeader);

    }

    @Override
    public void updateUser(UpdateUserRequest request, String authHeader) {
        sendPutRequest(request,"/auth-service/api/account", authHeader);
    }

    @Override
    public void changeEmail(ChangeEmailRequest request, String authHeader) {
        sendPutRequest(request,"/auth-service/api/account/email", authHeader);
    }
}