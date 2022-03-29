package dev._2lstudios.advancedparties.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev._2lstudios.advancedparties.AdvancedParties;

public class PlayerJoinListener implements Listener {
  private AdvancedParties plugin;

  public PlayerJoinListener(AdvancedParties plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    this.plugin.getPlayerManager().addPlayer(e.getPlayer()).download();
  }
}
