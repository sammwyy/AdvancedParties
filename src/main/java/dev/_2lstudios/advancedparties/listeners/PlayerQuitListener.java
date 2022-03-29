package dev._2lstudios.advancedparties.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev._2lstudios.advancedparties.AdvancedParties;

public class PlayerQuitListener implements Listener {
  private AdvancedParties plugin;

  public PlayerQuitListener(AdvancedParties plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    this.plugin.getPlayerManager().removePlayer(e.getPlayer());
  }
}
