package com.example.mytv.adapter.out;

import com.example.mytv.application.port.out.CacheManagePort;
import com.example.mytv.util.CacheNames;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheManagePersistenceAdapter implements CacheManagePort {
    private final StringRedisTemplate stringRedisTemplate;

    public CacheManagePersistenceAdapter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public List<String> getAllCacheNames() {
        return CacheNames.getCacheNames();
    }

    @Override
    public Boolean deleteCache(String cacheKey) {
        return stringRedisTemplate.delete(cacheKey);
    }

    List<String> getAllKeys() {
        return stringRedisTemplate.keys("*").stream().toList();
    }

    List<String> getAllKeys(String pattern) {
        return stringRedisTemplate.keys(pattern + "*").stream().toList();
    }
}
