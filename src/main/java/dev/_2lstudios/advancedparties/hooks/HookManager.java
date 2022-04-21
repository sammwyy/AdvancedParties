package dev._2lstudios.advancedparties.hooks;

import java.util.ArrayList;
import java.util.List;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.PartyHookPacket;

public class HookManager {
    private AdvancedParties plugin;

    private List<Hook> hooks;
    private List<Hook> loadedHooks;

    public HookManager(AdvancedParties plugin) {
        this.plugin = plugin;
        this.hooks = new ArrayList<>();
        this.loadedHooks = new ArrayList<>();
    }

    public void registerHook(Hook hook) {
        if (!this.hooks.contains(hook)) {
            this.hooks.add(hook);
        }
    }

    public void load(Hook hook) {
        if (!this.loadedHooks.contains(hook) && this.plugin.hasPlugin(hook.getPluginName())) {
            try {
                Class.forName(hook.getClassName());
            } catch (Exception ignored) {
                return;
            }

            hook.onLoad(this.plugin);
            this.loadedHooks.add(hook);
            this.plugin.getLogger().info("Added plugin hook for " + hook.getName());
        }
    }

    public void load() {
        for (Hook hook : this.hooks) {
            this.load(hook);
        }
    }

    public void processPacket(PartyHookPacket packet) {
        for (Hook hook : this.loadedHooks) {
            if (hook.getName().equalsIgnoreCase(packet.getHookName())) {
                hook.handlePacket(packet);
            }
        }
    }
}
