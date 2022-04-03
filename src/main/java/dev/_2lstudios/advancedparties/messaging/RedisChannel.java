package dev._2lstudios.advancedparties.messaging;

public class RedisChannel {
  private static final String prefix = "party_";

  public static String PARTY_INVITE = prefix + "invite";
  public static String PARTY_KICK = prefix + "kick";
  public static String PARTY_JOIN = prefix + "join";
  public static String PARTY_UPDATE = prefix + "update";
}
