package dev._2lstudios.advancedparties.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.api.events.PartyChatEvent;
import dev._2lstudios.advancedparties.messaging.packets.PartyChatPacket;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class AsyncChatListener implements Listener {
  private AdvancedParties plugin;

  public AsyncChatListener(AdvancedParties plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlayerChat(AsyncPlayerChatEvent e) {
    PartyPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
    
    if (player.isInParty() && player.getPartyChat()) {
      e.setCancelled(true);

      PartyChatPacket packet = new PartyChatPacket(player.getPartyID(), player.getName(), e.getMessage());
      PartyChatEvent event = new PartyChatEvent(packet, player);
      
      if (this.plugin.callEvent(event)) {
        this.plugin.getPubSub().publish(packet);
      }
    }
  }
}
