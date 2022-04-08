package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;

public class PartyInvitePacket implements Packet {
  private String source;
  private String target;
  private String party;
  
  public PartyInvitePacket(String source, String target, String party) {
    this.source = source;
    this.target = target;
    this.party = party;
  }

  public String getSourceName() {
    return this.source;
  }

  public String getTargetName() {
    return this.target;
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_INVITE;
  }
}
