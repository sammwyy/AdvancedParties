package dev._2lstudios.advancedparties.cache.impl;

import dev._2lstudios.advancedparties.cache.CacheEngine;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolCache implements CacheEngine {
    private JedisPool pool;

    public RedisPoolCache(String uri) {
        this.pool = new JedisPool(new JedisPoolConfig(), uri);
    }

    @Override
    public String get(String key) {
        String result = null;
        try (Jedis client = this.pool.getResource()) {
            result = client.get(key);
        }
        return result;
    }

    @Override
    public void set(String key, int expiration, String value) {
        try (Jedis client = this.pool.getResource()) {
            client.setex(key, expiration, value);
        }
    }

    @Override
    public void delete(String key) {
        try (Jedis client = this.pool.getResource()) {
            client.del(key);
        }
    }
}
