package dev._2lstudios.advancedparties.messaging;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.PartyInvitePacket;

public class RedisHandler {
    private AdvancedParties plugin;

    public RedisHandler(AdvancedParties plugin) {
        this.plugin = plugin;
    }

    public void handle(PartyInvitePacket packet) {
        this.plugin.getServer().broadcast("uwu", "");
    }
}
