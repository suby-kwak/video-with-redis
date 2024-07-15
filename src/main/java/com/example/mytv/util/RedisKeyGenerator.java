package com.example.mytv.util;

import static com.example.mytv.util.CacheKeyNames.VIDEO_VIEW_COUNT;

public class RedisKeyGenerator {
    public static String getVideoViewCountKey(String videoId) {
        return VIDEO_VIEW_COUNT + ":" +  videoId;
    }
}
