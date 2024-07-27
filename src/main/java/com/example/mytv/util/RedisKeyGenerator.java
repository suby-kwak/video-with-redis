package com.example.mytv.util;

import static com.example.mytv.util.CacheNames.SEPARATOR;
import static com.example.mytv.util.CacheNames.SUBSCRIBE_CHANNEL_BY_USER;
import static com.example.mytv.util.CacheNames.SUBSCRIBE_USER;
import static com.example.mytv.util.CacheNames.USER_SESSION;
import static com.example.mytv.util.CacheNames.VIDEO_LIKE;
import static com.example.mytv.util.CacheNames.VIDEO_VIEW_COUNT;

public class RedisKeyGenerator {
    public static String getUserSessionKey(String authKey) {
        return USER_SESSION + SEPARATOR + authKey;
    }

    public static String getSubscribeChannelKey(String channelId) {
        return SUBSCRIBE_CHANNEL_BY_USER + SEPARATOR + channelId;
    }

    public static String getSubscribeUserKey(String userId) {
        return SUBSCRIBE_USER + SEPARATOR + userId;
    }

    public static String getVideoViewCountKey(String videoId) {
        return VIDEO_VIEW_COUNT + SEPARATOR +  videoId;
    }

    public static String getVideoLikeKey(String videoId) {
        return VIDEO_LIKE + SEPARATOR +  videoId;
    }
}
