package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;

public class PartyJoinPacket implements Packet {
  private String player;
  private String party;
  
  public PartyJoinPacket(String player, String party) {
    this.player = player;
    this.party = party;
  }

  public String getPlayerName() {
    return this.player;
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_JOIN;
  }
}
