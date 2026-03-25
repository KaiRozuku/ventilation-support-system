package com.ipze.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class TokenRedisServiceImpl implements TokenRedisService {

    @Value("${jwt.refresh-token-ttl}")
    private Long refreshTokenTtl;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveRefreshToken(String username, String token) {
        String key = "refresh:" + username;
        redisTemplate.opsForValue().set(key, token, refreshTokenTtl, TimeUnit.SECONDS);
    }

    @Override
    public boolean validateRefreshToken(String username, String token) {
        String key = "refresh:" + username;
        String storedToken = (String) redisTemplate.opsForValue().get(key);
        return token.equals(storedToken);
    }

    @Override
    public void revokeAccessToken(String jti, long expiryMillis) {
        redisTemplate.opsForValue().set("blacklist:" + jti,
                "true",
                expiryMillis,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean isAccessTokenRevoked(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + jti));
    }
}