package com.example.mytv.application.port.out;

import java.util.List;

public interface CacheManagePort {
    List<String> getAllCacheKeys();

    List<String> getAllCacheKeys(String pattern);

    Boolean deleteCache(String cacheKey);
}
