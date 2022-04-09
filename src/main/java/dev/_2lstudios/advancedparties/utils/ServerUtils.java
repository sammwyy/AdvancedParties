package dev._2lstudios.advancedparties.utils;

enum VALUE {
    YES, NO, UNKNOWN
}

public class ServerUtils {
    private static VALUE HAS_CHAT_COMPONENT_API = VALUE.UNKNOWN;
    
    public static boolean hasChatComponentAPI() {
        if (HAS_CHAT_COMPONENT_API == VALUE.UNKNOWN) {
            try {
                Class.forName("net.md_5.bungee.api.ChatColor");
                HAS_CHAT_COMPONENT_API = VALUE.YES;
            } catch (ClassNotFoundException e) {
                HAS_CHAT_COMPONENT_API = VALUE.NO;
            }
        }

        return HAS_CHAT_COMPONENT_API == VALUE.YES;
    }
}
