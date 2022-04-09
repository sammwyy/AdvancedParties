package dev._2lstudios.advancedparties.cache.impl;

import dev._2lstudios.advancedparties.cache.CacheEngine;
import redis.clients.jedis.Jedis;

public class RedisCache implements CacheEngine {
    private Jedis cache;

    public RedisCache(String uri) {
        this.cache = new Jedis(uri);
    }

    @Override
    public String get(String key) {
        return this.cache.get(key);
    }

    @Override
    public void set(String key, int expiration, String value) {
        this.cache.setex(key, expiration, value);
    }

    @Override
    public void delete(String key) {
        this.cache.del(key);
    }
}
