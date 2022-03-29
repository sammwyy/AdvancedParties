package dev._2lstudios.advancedparties;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.providers.Provider;
import com.dotphin.milkshakeorm.repository.Repository;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.commands.impl.PartyCommand;
import dev._2lstudios.advancedparties.config.ConfigManager;
import dev._2lstudios.advancedparties.config.Configuration;
import dev._2lstudios.advancedparties.i18n.LanguageManager;
import dev._2lstudios.advancedparties.listeners.PlayerJoinListener;
import dev._2lstudios.advancedparties.listeners.PlayerQuitListener;
import dev._2lstudios.advancedparties.parties.PartyData;
import dev._2lstudios.advancedparties.parties.PartyManager;
import dev._2lstudios.advancedparties.players.PartyPlayerData;
import dev._2lstudios.advancedparties.players.PartyPlayerManager;

public class AdvancedParties extends JavaPlugin {
    private ConfigManager configManager;
    private LanguageManager languageManager;
    private PartyManager partyManager;
    private PartyPlayerManager playerManager;

    private Repository<PartyData> partyDataRepository;
    private Repository<PartyPlayerData> playerDataRepository;

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
        this.partyManager = new PartyManager(this);
        this.playerManager = new PartyPlayerManager(this);


        // Connect to database.
        Provider provider = MilkshakeORM.connect(this.getConfig().getString("settings.mongo-uri"));
        this.partyDataRepository = MilkshakeORM.addRepository(PartyData.class, provider, "Parties");
        this.playerDataRepository = MilkshakeORM.addRepository(PartyPlayerData.class, provider, "PartyPlayers");

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

    public PartyManager getPartyManager() {
        return this.partyManager;
    }

    public PartyPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    // Repository getters
    public Repository<PartyData> getPartyRepository() {
        return this.partyDataRepository;
    }

    public Repository<PartyPlayerData> getPlayerRepository() {
        return this.playerDataRepository;
    }
}