package dev._2lstudios.advancedparties.utils;

import org.bukkit.Bukkit;

public class ReflectionUtils {
    public static String PACKAGE = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static Class<?> getCBClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + PACKAGE + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + PACKAGE + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}