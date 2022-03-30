package dev._2lstudios.advancedparties.tasks;

import dev._2lstudios.advancedparties.AdvancedParties;

public class RequestExpirationTask implements Runnable {
    private AdvancedParties plugin;

    public RequestExpirationTask(AdvancedParties plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.plugin.getRequestManager().handleExpiration();
    }
}
