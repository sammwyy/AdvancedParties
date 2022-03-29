package dev._2lstudios.advancedparties;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.commands.PartyCommand;
import dev._2lstudios.advancedparties.config.ConfigManager;
import dev._2lstudios.advancedparties.config.Configuration;
import dev._2lstudios.advancedparties.i18n.LanguageManager;
import dev._2lstudios.advancedparties.listeners.PlayerJoinListener;
import dev._2lstudios.advancedparties.listeners.PlayerQuitListener;
import dev._2lstudios.advancedparties.players.PartyPlayerManager;

public class AdvancedParties extends JavaPlugin {
    private ConfigManager configManager;
    private LanguageManager languageManager;
    private PartyPlayerManager playerManager;

    private void addCommand(CommandListener command) {
        command.register(this, false);
    }

    private void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
    
    @Override
    public void onEnable () {
        // Instantiate managers.
        this.configManager = new ConfigManager(this);
        this.languageManager = new LanguageManager(this.getConfig().getString("settings.default-lang"), this.getDataFolder());
        this.playerManager = new PartyPlayerManager(this);

        // Load data.
        this.languageManager.loadLanguagesSafe();
        this.playerManager.addAll();

        // Register listeners.
        this.addListener(new PlayerJoinListener(this));
        this.addListener(new PlayerQuitListener(this));

        // Register commands.
        this.addCommand(new PartyCommand());
    }

    // Configuration getters
    public Configuration getConfig() {
        return this.configManager.getConfig("config.yml");
    }

    // Managers getters
    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    public PartyPlayerManager getPlayerManager() {
        return this.playerManager;
    }
}