package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;
import dev._2lstudios.advancedparties.requests.PartyRequest;

public class PartyInvitePacket implements Packet {
  private String source;
  private String target;
  private String party;
  
  public PartyInvitePacket(PartyRequest request) {
    this.source = request.source;
    this.target = request.target;
    this.party = request.party;
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
