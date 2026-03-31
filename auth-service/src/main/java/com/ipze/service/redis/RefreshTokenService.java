package com.ipze.service.redis;

import com.ipze.dto.request.UserDto;
import com.ipze.model.redis.RefreshToken;

public interface RefreshTokenService {

    void writeRefreshToken(String email, String token);

    UserDto getUserFromRefreshToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);

    boolean isBlacklisted(String jti);

    void blacklist(String jti, long ttl);
}