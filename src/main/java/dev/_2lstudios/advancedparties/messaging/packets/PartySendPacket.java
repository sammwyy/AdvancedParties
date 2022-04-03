package dev._2lstudios.advancedparties.messaging.packets;

import dev._2lstudios.advancedparties.messaging.RedisChannel;

public class PartySendPacket implements Packet {
  private String party;
  private String server;
  
  public PartySendPacket(String party, String server) {
    this.party = party;
    this.server = server;
  }

  public String getServer() {
    return this.server;
  }

  public String getPartyID() {
    return this.party;
  }

  @Override
  public String getChannel() {
    return RedisChannel.PARTY_SEND;
  }
}
