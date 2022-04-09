package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;
import dev._2lstudios.advancedparties.parties.PartyDisbandReason;

public class PartyDisbandPacket implements Packet {
  private String party;
  private String reason;
  
  public PartyDisbandPacket(String party, PartyDisbandReason reason) {
    this.party = party;
    this.reason = reason.name();
  }

  public PartyDisbandReason getReason() {
    return PartyDisbandReason.valueOf(this.reason);
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_DISBAND;
  }
}
