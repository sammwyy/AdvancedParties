package dev._2lstudios.advancedparties.parties;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitTask;

import dev._2lstudios.advancedparties.AdvancedParties;

public class PartyDisbandHandler {
    private AdvancedParties plugin;
    private Map<String, BukkitTask> parties;

    public PartyDisbandHandler(AdvancedParties plugin) {
        this.plugin = plugin;
        this.parties = new HashMap<>();
    }

    public void addPartyToDisband(Party party) {
        int timeout = this.plugin.getConfig().getInt("parties.disband-timeout");
        String id = party.getID();
        String server = this.plugin.getTempServerID();
        String key = "party_" + id + "_disband";

        this.plugin.getCache().set(key, timeout * 10, server);

        this.parties.put(
            id,
            this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                if (server.equals(this.plugin.getCache().get(key))) {
                    party.disband(PartyDisbandReason.TIMEOUT);
                }
            }, timeout * 20L)
        );
    }

    public void removePartyDisband(Party party) {
        String id = party.getID();
        String key = "party_" + id + "_disband";
        this.plugin.getCache().delete(key);

        BukkitTask task = this.parties.remove(id);
        if (task != null) {
            task.cancel();
        }
    }
}
