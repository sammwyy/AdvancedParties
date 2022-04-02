package dev._2lstudios.advancedparties.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

public class PacketUtils {
    public static Class<?> nmspacket = ReflectionUtils.getNMSClass("Packet");
    public static Class<?> nmschatpacket = ReflectionUtils.getNMSClass("PacketPlayOutChat");
    public static Class<?> nmschatcomponent = ReflectionUtils.getNMSClass("IChatBaseComponent");
    public static Class<?> nmschatserializer = ReflectionUtils.getNMSClass("IChatBaseComponent$ChatSerializer");
    public static Class<?> nmsplayerconnection = ReflectionUtils.getNMSClass("PlayerConnection");
    public static Class<?> craftplayer = ReflectionUtils.getCBClass("entity.CraftPlayer");
    public static Class<?> nmsentityplayer = ReflectionUtils.getNMSClass("EntityPlayer");


    public static void sendJSON (Player player, String json, byte type) {
 
        try {
            Object chatcomponent = nmschatserializer.getMethod("a", String.class).invoke(null, json);
            Object packet = null;
            for (Constructor<?> constructor : nmschatpacket.getConstructors()) {
                Class<?>[] types = constructor.getParameterTypes();
                if(types.length == 2) {
                    if(types[1] == byte.class) {
                        packet = nmschatpacket.getConstructor(nmschatcomponent, byte.class).newInstance(chatcomponent, type);
                    } else {
                        Class<?> nmschatmessagetype = ReflectionUtils.getNMSClass("ChatMessageType");
                        packet = nmschatpacket.getConstructor(nmschatcomponent, nmschatmessagetype).newInstance(chatcomponent, Array.get(nmschatmessagetype.getMethod("values").invoke(null), type));
                    }
                }
            }
            Object craftPlayer = craftplayer.cast(player);
            Object entityPlayer = craftplayer.getMethod("getHandle").invoke(craftPlayer);
            Object playerConnection = nmsentityplayer.getField("playerConnection").get(entityPlayer);
            nmsplayerconnection.getMethod("sendPacket", nmspacket).invoke(playerConnection, nmspacket.cast(packet));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
     
    }
}