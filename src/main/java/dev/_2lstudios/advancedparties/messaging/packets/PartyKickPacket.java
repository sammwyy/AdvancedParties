package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;

public class PartyKickPacket implements Packet {
  private String party;
  private String target;
  
  public PartyKickPacket(String party, String target) {
    this.party = party;
    this.target = target;
  }

  public String getTargetName() {
    return this.target;
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_KICK;
  }
}
