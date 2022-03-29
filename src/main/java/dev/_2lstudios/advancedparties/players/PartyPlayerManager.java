package dev._2lstudios.advancedparties.players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;

public class PartyPlayerManager {
    private AdvancedParties plugin;

    private Map<Player, PartyPlayer> players;

    public PartyPlayerManager(AdvancedParties plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    public PartyPlayer addPlayer(Player bukkitPlayer) {
      PartyPlayer player = new PartyPlayer(this.plugin, bukkitPlayer);
        this.players.put(bukkitPlayer, player);
        return player;
    }

    public PartyPlayer removePlayer(Player bukkitPlayer) {
        return this.players.remove(bukkitPlayer);
    }

    public PartyPlayer getPlayer(Player bukkitPlayer) {
        return this.players.get(bukkitPlayer);
    }

    public PartyPlayer getPlayer(String name) {
        Player bukkitPlayer = this.plugin.getServer().getPlayer(name);
        if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
            return this.getPlayer(bukkitPlayer);
        } else {
            return null;
        }
    }

    public Collection<PartyPlayer> getPlayers() {
        return this.players.values();
    }

    public void clear() {
        this.players.clear();
    }

    public void addAll() {
        for (Player bukkitPlayer : this.plugin.getServer().getOnlinePlayers()) {
            this.addPlayer(bukkitPlayer).download();
        }
    }
}