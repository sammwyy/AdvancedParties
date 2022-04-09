package dev._2lstudios.advancedparties.api.events;

import org.bukkit.event.HandlerList;

import dev._2lstudios.advancedparties.messaging.packets.PartyInvitePacket;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartyInviteEvent extends PartyEvent {
    private static final HandlerList handlers = new HandlerList();

    private PartyInvitePacket packet;
    private PartyPlayer player;

    public PartyInviteEvent(PartyInvitePacket packet, PartyPlayer player) {
        this.packet = packet;
        this.player = player;
    }

    public Party getParty() {
        return this.player.getParty();
    }

    public PartyPlayer getPlayer() {
        return this.player;
    }

    public String getTarget() {
        return this.packet.getTargetName();
    }

    public String getPartyID() {
        return this.packet.getPartyID();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
