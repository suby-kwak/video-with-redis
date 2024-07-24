package com.example.mytv.util;

import static com.example.mytv.util.CacheNames.SEPARATOR;
import static com.example.mytv.util.CacheNames.VIDEO_LIKE;
import static com.example.mytv.util.CacheNames.VIDEO_VIEW_COUNT;

public class RedisKeyGenerator {
    public static String getVideoViewCountKey(String videoId) {
        return VIDEO_VIEW_COUNT + SEPARATOR +  videoId;
    }

    public static String getVideoLikeKey(String videoId) {
        return VIDEO_LIKE + SEPARATOR +  videoId;
    }
}
