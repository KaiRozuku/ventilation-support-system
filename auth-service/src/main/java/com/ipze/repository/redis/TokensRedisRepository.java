package com.ipze.repository.redis;

import com.ipze.model.redis.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRedisRepository extends CrudRepository<Token, String> {
}