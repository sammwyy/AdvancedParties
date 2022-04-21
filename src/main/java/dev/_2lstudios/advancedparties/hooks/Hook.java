package dev._2lstudios.advancedparties.hooks;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.PartyHookPacket;

public interface Hook {
    public String getName();
    public String getPluginName();
    public String getClassName();

    public void onLoad(AdvancedParties plugin);
    public void handlePacket(PartyHookPacket packet);
}
