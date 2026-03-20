//package com.ipze.service.redis;
//
//import com.ipze.model.redis.Token;
//import com.ipze.repository.redis.TokensRedisRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
////@Service
//@RequiredArgsConstructor
//public class TokensRedisService {
//
//    private final TokensRedisRepository tokensRedisRepository;
//
//    public void save(Token entity) {
//        tokensRedisRepository.save(entity);
//    }
//
//    public Optional<Token> findById(String id) {
//        return tokensRedisRepository.findById(id);
//    }
//
//    public Iterable<Token> findAll() {
//        return tokensRedisRepository.findAll();
//    }
//}