package com.example.bms.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService implements CacheService {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, value.toString(), 2, TimeUnit.MINUTES);
    }

    @Override
    public Object get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void getAllKeysAndValues() {
        stringRedisTemplate.keys("*").forEach(key -> {
            System.out.println("Key: " + key + " Value: " + stringRedisTemplate.opsForValue().get(key));
        });
    }

    @Override
    public void deleteAll() {
        stringRedisTemplate.delete(stringRedisTemplate.keys("*"));
    }
}
