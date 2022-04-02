package dev._2lstudios.advancedparties.messaging;

public class RedisChannel {
  private static final String prefix = "party_";

  public static String PARTY_INVITE = prefix + "invite";
  public static String PARTY_KICK = prefix + "kick";
}
