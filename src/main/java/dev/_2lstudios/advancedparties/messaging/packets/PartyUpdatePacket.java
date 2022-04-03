package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;

public class PartyUpdatePacket implements Packet {
  private String party;
  
  public PartyUpdatePacket(String party) {
    this.party = party;
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_UPDATE;
  }
}
