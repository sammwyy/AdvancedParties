package dev._2lstudios.advancedparties.api.events;

import org.bukkit.event.HandlerList;

import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartyCreateEvent extends PartyEvent {
    private static final HandlerList handlers = new HandlerList();

    private PartyPlayer player;

    public PartyCreateEvent(PartyPlayer player) {
        this.player = player;
    }

    public PartyPlayer getPlayer() {
        return this.player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
