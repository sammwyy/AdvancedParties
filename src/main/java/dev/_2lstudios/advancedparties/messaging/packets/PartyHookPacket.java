package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;

public class PartyHookPacket implements Packet {
  private String party;
  private String player;
  private String hookName;
  private String message;
  
  public PartyHookPacket(String party, String player, String hookName, String message) {
    this.party = party;
    this.player = player;
    this.hookName = hookName;
    this.message = message;
  }

  public String getPlayerName() {
    return this.player;
  }

  public String getHookName() {
    return this.hookName;
  }

  public String getMessage() {
    return this.message;
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_HOOK;
  }
}
