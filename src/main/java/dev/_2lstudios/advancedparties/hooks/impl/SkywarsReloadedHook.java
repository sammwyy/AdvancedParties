package dev._2lstudios.advancedparties.hooks.impl;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.events.SkyWarsJoinEvent;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.game.TeamCard;
import com.walrusone.skywarsreloaded.utilities.SWRServer;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.hooks.Hook;
import dev._2lstudios.advancedparties.messaging.packets.PartyHookPacket;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class SkywarsReloadedHook implements Hook, Listener {
    private AdvancedParties plugin;

    @Override
    public String getName() {
        return "Skywars Reloaded";
    }

    @Override
    public String getPluginName() {
        return "Skywars";
    }

    @Override
    public String getClassName() {
        return "com.walrusone.skywarsreloaded.SkyWarsReloaded";
    }

    @Override
    public void onLoad(AdvancedParties plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void publishPacket(PartyPlayer player, String message) {
        String party = player.getPartyID();
        this.plugin.getPubSub().publish(new PartyHookPacket(party, player.getName(), this.getName(), message));
    }

    @EventHandler
    public void onSkywarsJoin(SkyWarsJoinEvent e) {
        PartyPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
        
        if (player.isInParty() && player.getParty().isLeader(player)) {
            GameMap game = e.getGame();
            this.publishPacket(player, "join::" + game.getName());
        }
    }

    private void handleJoin(PartyHookPacket packet, String arg) {
        String partyID = packet.getPartyID();
        String sender = packet.getPlayerName();

        SWRServer server = null;
        GameMap map = null;
        TeamCard team = null;

        if (SkyWarsReloaded.getCfg().bungeeMode()) {
            server = SWRServer.getServer(arg);
        } else {
            map = SkyWarsReloaded.getAPI().getGameAPI().getGame(arg);
            if (map.getTeamSize() != 1) {
                team = map.getTeamCardFromName(sender);
            }
        }

        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            GameMap currentGame = SkyWarsReloaded.getAPI().getGameAPI().getGame(player.getBukkitPlayer());
            if (currentGame == null) {
                if (player.isInParty() && player.getPartyID().equals(partyID) && !player.getName().equalsIgnoreCase(sender)) {
                    if (server != null && server.canAddPlayer()) {
                        server.setPlayerCount(server.getPlayerCount() + 1);
                        server.updateSigns();
                        SkyWarsReloaded.get().sendBungeeMsg(player.getBukkitPlayer(), "Connect", server.getServerName());
                    }

                    else if (map != null && map.canAddPlayer()) {
                        if (team != null  && team.getPlayersSize() < team.getSize()) {
                            map.addPlayers(team, player.getBukkitPlayer());
                        } else {
                            map.addPlayers(null, player.getBukkitPlayer());
                        }
                    }
                }
            }
        }
    }

    public void handleSyncPacket(PartyHookPacket packet) {
        String message = packet.getMessage();
        String action = message.split("::")[0];
        String arg = message.split("::")[1];

        switch (action) {
            case "join":
                this.handleJoin(packet, arg);
                break;
            default:
                break;
        }
    }

    @Override
    public void handlePacket(PartyHookPacket packet) {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            this.handleSyncPacket(packet);
        });
    }
}
