package dev._2lstudios.advancedparties.config;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration extends YamlConfiguration {
    private final File file;

    public Configuration(final File file) {
        this.file = file;
    }

    /* Utils methods */
    public void load() throws FileNotFoundException, IOException, InvalidConfigurationException {
        this.load(this.file);
    }

    public void save() throws IOException {
        this.save(this.file);
    }

    public void safeSave() {
        try {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIfNotExist(final String path, final Object value) {
        if (!this.contains(path) && value != null) {
            this.set(path, value);
            this.safeSave();
        }
    }

    /* Primitive object get and set */
    @Override
    public int getInt(final String path, final int defaultValue) {
        this.setIfNotExist(path, defaultValue);
        return super.getInt(path, defaultValue);
    }

    @Override
    public String getString(final String path, final String defaultValue) {
        this.setIfNotExist(path, defaultValue);
        return super.getString(path, defaultValue);
    }

    @Override
    public boolean getBoolean(final String path, final boolean defaultValue) {
        this.setIfNotExist(path, defaultValue);
        return super.getBoolean(path, defaultValue);
    }

    public List<String> getStringList(final String path, final List<String> defaultValue) {
        this.setIfNotExist(path, defaultValue);
        return super.getStringList(path);
    }

    /* Custom object get and set */
    public Material getMaterial(final String key) {
        final String name = this.getString(key);

        for (final Material mat : Material.values()) {
            if (name.equals(mat.name())) {
                return mat;
            }
        }

        Bukkit.getLogger().warning("Couldn't load material '" + name + "' from configuration file! (Invalid name?)");
        return null;
    }

    public Material getMaterial(final String key, final String defaultValue) {
        this.setIfNotExist(key, defaultValue);
        return this.getMaterial(key);
    }

    public Material getMaterial(final String key, final Material defaultValue) {
        return this.getMaterial(key, defaultValue.toString());
    }

    public Sound getSound(final String key) {
        final String name = this.getString(key);

        for (final Sound sound : Sound.values()) {
            if (name.equalsIgnoreCase(sound.name())) {
                return sound;
            }
        }

        Bukkit.getLogger().warning("Couldn't load sound '" + name + "' from configuration file! (Invalid name?)");
        return null;
    }

    public Sound getSound(final String key, final String defaultValue) {
        this.setIfNotExist(key, defaultValue);
        return this.getSound(key);
    }

    public Sound getSound(final String key, final Sound defaultValue) {
        return this.getSound(key, defaultValue.toString());
    }

    public void setLocation(final String key, final Location location, final boolean includeWorld) {
        if (includeWorld) {
            this.set(key + ".world", location.getWorld().getName());
        }

        this.set(key + ".x", location.getX());
        this.set(key + ".y", location.getY());
        this.set(key + ".z", location.getZ());
        this.set(key + ".pitch", location.getPitch());
        this.set(key + ".yaw", location.getYaw());
    }

    public void setLocation(final String key, final Location location) {
        this.setLocation(key, location, true);
    }
}