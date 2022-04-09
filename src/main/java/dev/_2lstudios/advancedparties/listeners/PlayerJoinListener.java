package dev._2lstudios.advancedparties.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PlayerJoinListener implements Listener {
  private AdvancedParties plugin;

  public PlayerJoinListener(AdvancedParties plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    PartyPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());
    player.download();

    if (player.isInParty()) {
      if (player.getParty().isLeader(player)) {
        this.plugin.getPartyDisbandHandler().removePartyDisband(player.getParty());
      }
    }
  }
}
