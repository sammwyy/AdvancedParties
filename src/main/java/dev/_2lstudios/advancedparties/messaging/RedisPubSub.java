package dev._2lstudios.advancedparties.messaging;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSub {
  private Jedis suscriber;
  private Jedis publisher;

  private String[] channels = new String[]{
    RedisChannel.PARTY_INVITE.name()
  };

  public RedisPubSub(String redisURI) {
    this.suscriber = new Jedis(redisURI);
    this.publisher = new Jedis(redisURI);

    new Thread(() -> {
      suscriber.subscribe(new JedisPubSub() {
        
      }, channels);
    }).start();
  }
}
