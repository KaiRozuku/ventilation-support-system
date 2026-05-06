package com.ipze.service.redis;

import com.ipze.dto.request.UserDto;
import com.ipze.exception.TokenNotFoundException;
import com.ipze.exception.UserNotFoundException;
import com.ipze.mapper.UserMapper;
import com.ipze.model.redis.RefreshToken;
import com.ipze.repository.UserRepository;
import com.ipze.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.access-token-ttl}")
    private Long ttlSeconds;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:jti:" + jti));
    }

    @Override
    public void blacklist(String jti, long expirationTime) {
        long ttl = expirationTime - System.currentTimeMillis();

        if (ttl > 0) {
            redisTemplate
                    .opsForValue()
                    .set("blacklist:jti:" + jti, "1", Duration.ofMillis(ttl));
        }
    }

    @Override
    public void writeRefreshToken(String email, String token) {

        var user = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        log.info("Written refreshToken to Redis {}", token);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID())
                        .userId(user.getUserID().toString())
                        .token(token)
                        .expiryDate(Instant.now().plusSeconds(ttlSeconds))
                        .ttl(ttlSeconds)
                        .build()
        );
    }

    @Override
    public UserDto getUserFromRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .map(this::verifyExpiration)
                .orElseThrow(() -> new TokenNotFoundException("Refresh refreshToken not found or expired"));
        log.info("refreshToken {} from user ?", refreshToken);
        return userMapper.toDto(
                userRepository.findById(UUID.fromString(refreshToken.getUserId()))
                        .orElseThrow(UserNotFoundException::new)
        );
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            log.info("Deleted refreshToken {}", token);
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh refreshToken is expired. Please make a new login..!");
        }
        return token;
    }
}