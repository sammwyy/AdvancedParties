package dev._2lstudios.advancedparties.messaging;

import com.google.gson.Gson;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.Packet;
import dev._2lstudios.advancedparties.messaging.packets.PartyInvitePacket;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSub {
    private RedisHandler handler;
    private Gson gson;
    private Jedis suscriber;
    private Jedis publisher;
    private JedisPubSub pubsub;

    private String[] channels = new String[] {
            RedisChannel.PARTY_INVITE
    };

    public RedisPubSub(AdvancedParties plugin, String redisURI) {
        this.handler = new RedisHandler(plugin);
        this.gson = new Gson();
        this.suscriber = new Jedis(redisURI);
        this.publisher = new Jedis(redisURI);
        this.pubsub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                process(channel, message);
            }
        };

        new Thread(() -> {
            try {
                suscriber.subscribe(pubsub, channels);
            } catch (Exception ignored) {}
        }).start();
    }

    private void process(String channel, String message) {
        if (channel.equalsIgnoreCase(RedisChannel.PARTY_INVITE)) {
            handler.handle(gson.fromJson(message, PartyInvitePacket.class));
        }
    }

    public void publish(Packet packet) {
        this.publisher.publish(packet.getChannel(), gson.toJson(packet));
    }

    public void disconnect() {
        this.pubsub.unsubscribe(channels);
        this.publisher.close();
        this.suscriber.close();
    }
}