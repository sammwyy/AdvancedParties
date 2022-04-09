package dev._2lstudios.advancedparties.api.events;

import org.bukkit.event.HandlerList;

import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartyAcceptEvent extends PartyEvent {
    private static final HandlerList handlers = new HandlerList();

    private String partyID;
    private PartyPlayer player;

    public PartyAcceptEvent(String partyID, PartyPlayer player) {
        this.partyID = partyID;
        this.player = player;
    }

    public Party getParty() {
        return this.player.getPlugin().getPartyManager().getParty(this.partyID);
    }

    public PartyPlayer getPlayer() {
        return this.player;
    }

    public String getPartyID() {
        return this.partyID;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
