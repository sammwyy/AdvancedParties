package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;

public class PartyChatPacket implements Packet {
  private String party;
  private String player;
  private String message;
  
  public PartyChatPacket(String party, String player, String message) {
    this.party = party;
    this.player = player;
    this.message = message;
  }

  public String getPlayerName() {
    return this.player;
  }

  public String getMessage() {
    return this.message;
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_CHAT;
  }
}
