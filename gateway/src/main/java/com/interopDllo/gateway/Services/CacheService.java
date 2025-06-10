package com.interopDllo.gateway.Services;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class CacheService {

    private final RedisTemplate<String, String> redisTemplate;

    public CacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Guardar en Redis con expiración
    public void saveResponse(String key, String response, long expirationInSeconds) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, response, Duration.ofSeconds(expirationInSeconds));
        System.out.println("✅ Guardado en Redis: " + key);
    }

    // Recuperar desde Redis
    public String getCachedResponse(String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value = operations.get(key);
        if (value != null) {
            System.out.println("🔵 Recuperado de Redis: " + key + " -> " + value);
        } else {
            System.out.println("🚨 Redis no tiene datos para: " + key);
        }
        return value;
    }
}
