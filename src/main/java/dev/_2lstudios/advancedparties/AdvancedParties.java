package dev._2lstudios.advancedparties;

import java.nio.charset.Charset;
import java.util.Random;

import com.dotphin.milkshakeorm.MilkshakeORM;
import com.dotphin.milkshakeorm.providers.Provider;
import com.dotphin.milkshakeorm.repository.Repository;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.advancedparties.api.PartyAPI;
import dev._2lstudios.advancedparties.cache.CacheEngine;
import dev._2lstudios.advancedparties.cache.impl.RedisCache;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.commands.impl.PartyCommand;
import dev._2lstudios.advancedparties.config.ConfigManager;
import dev._2lstudios.advancedparties.config.Configuration;
import dev._2lstudios.advancedparties.i18n.LanguageManager;
import dev._2lstudios.advancedparties.listeners.PlayerJoinListener;
import dev._2lstudios.advancedparties.listeners.PlayerQuitListener;
import dev._2lstudios.advancedparties.messaging.RedisPubSub;
import dev._2lstudios.advancedparties.parties.PartyData;
import dev._2lstudios.advancedparties.parties.PartyDisbandHandler;
import dev._2lstudios.advancedparties.parties.PartyManager;
import dev._2lstudios.advancedparties.players.PartyPlayerData;
import dev._2lstudios.advancedparties.players.PartyPlayerManager;
import dev._2lstudios.advancedparties.requests.PartyRequestManager;

public class AdvancedParties extends JavaPlugin {
    private ConfigManager configManager;
    private LanguageManager languageManager;
    private PartyDisbandHandler partyDisband;
    private PartyManager partyManager;
    private PartyPlayerManager playerManager;
    private PartyRequestManager requestManager;

    private RedisPubSub pubsub;
    private CacheEngine cache;

    private Repository<PartyData> partyDataRepository;
    private Repository<PartyPlayerData> playerDataRepository;

    private String tempServerID = null;

    private void addCommand(CommandListener command) {
        command.register(this, false);
    }

    private void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerOutgoingChannel(String channel) {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, channel);
    }
    
    @Override
    public void onEnable () {
        // Initialize API
        new PartyAPI(this);

        // Register plugin channels.
        this.registerOutgoingChannel("BungeeCord");

        // Instantiate managers.
        this.configManager = new ConfigManager(this);
        this.languageManager = new LanguageManager(this);
        this.partyDisband = new PartyDisbandHandler(this);
        this.partyManager = new PartyManager(this);
        this.playerManager = new PartyPlayerManager(this);
        this.requestManager = new PartyRequestManager(this);
        
        // Connect to redis.
        this.cache = new RedisCache(this.getConfig().getString("settings.redis-uri"));
        this.pubsub = new RedisPubSub(this, this.getConfig().getString("settings.redis-uri"));

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

    public PartyDisbandHandler getPartyDisbandHandler() {
        return this.partyDisband;
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
    public CacheEngine getCache() {
        return this.cache;
    }

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

    // Others getters
    public String getTempServerID() {
        if (this.tempServerID == null) {
            byte[] array = new byte[15];
            new Random().nextBytes(array);
            this.tempServerID = new String(array, Charset.forName("UTF-8"));
        }

        return this.tempServerID;
    }
}