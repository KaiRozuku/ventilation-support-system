package com.ipze.service.redis;

public interface TokenRedisService {

    void saveRefreshToken(String username, String token);

    boolean validateRefreshToken(String username, String token);

    void revokeAccessToken(String jti, long expiryMillis);

    boolean isAccessTokenRevoked(String jti);
}