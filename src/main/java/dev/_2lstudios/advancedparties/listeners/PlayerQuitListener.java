package dev._2lstudios.advancedparties.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PlayerQuitListener implements Listener {
  private AdvancedParties plugin;

  public PlayerQuitListener(AdvancedParties plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    PartyPlayer player = this.plugin.getPlayerManager().removePlayer(e.getPlayer());

    if (player.isInParty()) {
      Party party = player.getParty();
      if (party.isLeader(player)) {
        this.plugin.getPartyDisbandHandler().addPartyToDisband(party);
      }
    }
  }
}
