package dev._2lstudios.advancedparties.players;

import dev._2lstudios.advancedparties.AdvancedParties;

public class OfflinePlayer extends PartyPlayer {
    private String username;

    public OfflinePlayer(AdvancedParties plugin, String username) {
        super(plugin, null);
        this.username = username.toLowerCase();
    }

    @Override
    public String getLowerName() {
        return this.username;
    }
}