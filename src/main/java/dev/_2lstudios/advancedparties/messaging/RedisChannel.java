package dev._2lstudios.advancedparties.messaging;

public class RedisChannel {
  private static final String prefix = "party_";

  public static String PARTY_INVITE = prefix + "invite";
  public static String PARTY_KICK = prefix + "kick";
  public static String PARTY_JOIN = prefix + "join";
  public static String PARTY_UPDATE = prefix + "update";
  public static String PARTY_PROMOTE = prefix + "promote";
  public static String PARTY_DISBAND = prefix + "disband";
  public static String PARTY_SEND = prefix + "send";
  public static String PARTY_LEAVE = prefix + "leave";
  public static String PARTY_CHAT = prefix + "chat";
  public static String PARTY_HOOK = prefix + "hook";
}
