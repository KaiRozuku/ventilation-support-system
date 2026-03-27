package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.interfaces.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final WebClient webClient;

    private <T> void sendPutRequest(T request, String uri, HttpServletRequest servletRequest){

        String userId = servletRequest.getHeader("X-User-ID");
        String username = servletRequest.getHeader("X-User-Name");
        String roles = servletRequest.getHeader("X-User-Roles");
        String auth = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        webClient.put()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, auth)
                .header("X-User-ID", userId)
                .header("X-User-Name", username)
                .header("X-User-Roles", roles)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
    @Override
    public void changePassword(ChangePasswordRequest request, HttpServletRequest httpServletRequest) {
        sendPutRequest(request, "/auth-service/api/account/password", httpServletRequest);

    }

    @Override
    public void updateUser(UpdateUserRequest request, HttpServletRequest httpServletRequest) {
        sendPutRequest(request,"/auth-service/api/account", httpServletRequest);
    }

    @Override
    public void changeEmail(ChangeEmailRequest request, HttpServletRequest httpServletRequest) {
        sendPutRequest(request,"/auth-service/api/account/email", httpServletRequest);
    }
}