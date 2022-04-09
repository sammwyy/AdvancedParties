package dev._2lstudios.advancedparties.api.events;

import org.bukkit.event.HandlerList;

import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartySendEvent extends PartyEvent {
    private static final HandlerList handlers = new HandlerList();

    private Party party;
    private PartyPlayer player;
    private String server;

    public PartySendEvent(Party party, PartyPlayer player, String server) {
        this.party = party;
        this.player = player;
        this.server = server;
    }

    public Party getParty() {
        return this.party;
    }

    public PartyPlayer getPlayer() {
        return this.player;
    }

    public String getServer() {
        return this.server;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
