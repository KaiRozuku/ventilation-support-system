package com.ipze.client.interfaces;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import reactor.core.publisher.Mono;

public interface UserAccountClient {

    Mono<Void> changePassword(ChangePasswordRequest request);

    Mono<Void> updateUser(UpdateUserRequest request);

    Mono<Void> changeEmail(ChangeEmailRequest request);

    Mono<Void> logout();
}