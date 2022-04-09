package dev._2lstudios.advancedparties.api.events;

import org.bukkit.event.HandlerList;

import dev._2lstudios.advancedparties.messaging.packets.PartyChatPacket;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartyChatEvent extends PartyEvent {
    private static final HandlerList handlers = new HandlerList();

    private PartyChatPacket packet;
    private PartyPlayer player;

    public PartyChatEvent(PartyChatPacket packet, PartyPlayer player) {
        this.packet = packet;
        this.player = player;
    }

    public Party getParty() {
        return this.player.getParty();
    }

    public PartyPlayer getPlayer() {
        return this.player;
    }

    public String getPartyID() {
        return this.packet.getPartyID();
    }

    public String getPlayerName() {
        return this.packet.getPlayerName();
    }

    public String getMessage() {
        return this.packet.getMessage();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
