package dev._2lstudios.advancedparties.tasks;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class ServerPartySyncTask implements Runnable {
    private AdvancedParties plugin;

    public ServerPartySyncTask(AdvancedParties plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (PartyPlayer player : plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.isPartyLeader()) {
                int timeout = this.plugin.getConfig().getInt("parties.disband-timeout");
                String id = player.getPartyID();
                String server = this.plugin.getTempServerID();
                String key = "party_" + id + "_disband";

                this.plugin.getCache().set(key, timeout * 10, server);
            }
        }
    }
}
