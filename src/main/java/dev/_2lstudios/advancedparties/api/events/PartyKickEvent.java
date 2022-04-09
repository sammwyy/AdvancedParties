package dev._2lstudios.advancedparties.api.events;

import org.bukkit.event.HandlerList;

import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartyKickEvent extends PartyEvent {
    private static final HandlerList handlers = new HandlerList();

    private Party party;
    private PartyPlayer player;
    private String target;

    public PartyKickEvent(Party party, PartyPlayer player, String target) {
        this.party = party;
        this.player = player;
        this.target = target;
    }

    public Party getParty() {
        return this.party;
    }

    public PartyPlayer getPlayer() {
        return this.player;
    }

    public String getTarget() {
        return this.target;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
