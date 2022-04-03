package dev._2lstudios.advancedparties.api;

import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartyAPI {
    private static AdvancedParties plugin;
    
    public PartyAPI(AdvancedParties plugin) {
        PartyAPI.plugin = plugin;
    }

    public static PartyPlayer getPlayer(Player player) {
        return plugin.getPlayerManager().getPlayer(player);
    }

    public static Party getParty(String id) {
        return plugin.getPartyManager().getParty(id);
    }

    public static Party getParty(Player player) {
        PartyPlayer partyPlayer = PartyAPI.getPlayer(player);
        if (partyPlayer != null) {
            return partyPlayer.getParty();
        } else {
            return null;
        }
    }

    public boolean isInParty(Player player) {
        PartyPlayer partyPlayer = PartyAPI.getPlayer(player);
        if (partyPlayer != null) {
            return partyPlayer.isInParty();
        } else {
            return false;
        }
    }
}
