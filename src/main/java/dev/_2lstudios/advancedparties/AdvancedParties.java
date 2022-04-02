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
import dev._2lstudios.advancedparties.messaging.RedisPubSub;
import dev._2lstudios.advancedparties.parties.PartyData;
import dev._2lstudios.advancedparties.parties.PartyManager;
import dev._2lstudios.advancedparties.players.PartyPlayerData;
import dev._2lstudios.advancedparties.players.PartyPlayerManager;
import dev._2lstudios.advancedparties.requests.PartyRequest;
import dev._2lstudios.advancedparties.requests.PartyRequestManager;
import dev._2lstudios.advancedparties.tasks.RequestExpirationTask;

public class AdvancedParties extends JavaPlugin {
    private ConfigManager configManager;
    private LanguageManager languageManager;
    private PartyManager partyManager;
    private PartyPlayerManager playerManager;
    private PartyRequestManager requestManager;

    private RedisPubSub pubsub;

    private Repository<PartyData> partyDataRepository;
    private Repository<PartyPlayerData> playerDataRepository;
    private Repository<PartyRequest> requestsRepository;

    private void addCommand(CommandListener command) {
        command.register(this, false);
    }

    private void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private void addTaskTimer(Runnable task, long delay, long period) {
        this.getServer().getScheduler().runTaskTimer(this, task, delay, period);
    }

    private void addTaskTimer(Runnable task, long period) {
        this.addTaskTimer(task, 0, period);
    }
    
    @Override
    public void onEnable () {
        // Instantiate managers.
        this.configManager = new ConfigManager(this);
        this.languageManager = new LanguageManager(this.getConfig().getString("settings.default-lang"), this.getDataFolder());
        this.partyManager = new PartyManager(this);
        this.playerManager = new PartyPlayerManager(this);
        this.requestManager = new PartyRequestManager(this);
        
        // Connect to redis.
        this.pubsub = new RedisPubSub(this, this.getConfig().getString("settings.redis-uri"));

        // Connect to database.
        Provider provider = MilkshakeORM.connect(this.getConfig().getString("settings.mongo-uri"));
        this.partyDataRepository = MilkshakeORM.addRepository(PartyData.class, provider, "Parties");
        this.playerDataRepository = MilkshakeORM.addRepository(PartyPlayerData.class, provider, "PartyPlayers");
        this.requestsRepository = MilkshakeORM.addRepository(PartyRequest.class, provider, "PartyRequests");

        // Load data.
        this.languageManager.loadLanguagesSafe();
        this.playerManager.addAll();

        // Register listeners.
        this.addListener(new PlayerJoinListener(this));
        this.addListener(new PlayerQuitListener(this));

        // Register commands.
        this.addCommand(new PartyCommand());

        // Register tasks.
        this.addTaskTimer(new RequestExpirationTask(this), 30 * 20);
    }

    @Override
    public void onDisable() {
        this.pubsub.disconnect();
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

    public PartyRequestManager getRequestManager() {
        return this.requestManager;
    }

    // Redis getters
    public RedisPubSub getPubSub() {
        return this.pubsub;
    }

    // Repository getters
    public Repository<PartyData> getPartyRepository() {
        return this.partyDataRepository;
    }

    public Repository<PartyPlayerData> getPlayerRepository() {
        return this.playerDataRepository;
    }

    public Repository<PartyRequest> getRequestsRepository() {
        return this.requestsRepository;
    }
}