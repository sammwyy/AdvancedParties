package dev._2lstudios.advancedparties.players;

import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.commands.CommandExecutor;

public class PartyPlayer extends CommandExecutor {
  private Player bukkitPlayer;

  public PartyPlayer(AdvancedParties plugin, Player bukkitPlayer) {
    super(plugin, bukkitPlayer);
    this.bukkitPlayer = bukkitPlayer;
  }
  
  public Player getBukkitPlayer() {
    return this.bukkitPlayer;
  }

  public void download() {
    
  }
}
