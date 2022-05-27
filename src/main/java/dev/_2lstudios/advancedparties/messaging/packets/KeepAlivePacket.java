package dev._2lstudios.advancedparties.messaging.packets;

public class KeepAlivePacket implements Packet {
    @Override
    public String getChannel() {
        return "keepalive";
    }
}
