package com.example.mytv.application.port.out;

import java.util.List;

public interface CacheManagePort {
    List<String> getAllCacheNames();

    Boolean deleteCache(String cacheKey);
}
