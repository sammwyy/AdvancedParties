package dev._2lstudios.advancedparties;

import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedParties extends JavaPlugin {
    
    @Override
    public void onEnable () {
        // Save default config
        this.saveDefaultConfig();
    }
}