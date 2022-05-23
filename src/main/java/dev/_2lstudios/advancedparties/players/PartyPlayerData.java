package dev._2lstudios.advancedparties.players;

import com.dotphin.classserializer.annotations.Prop;
import com.dotphin.milkshake.Entity;

public class PartyPlayerData extends Entity {
    @Prop
    public String username;

    @Prop
    public String party;

    @Prop
    public boolean partyChat = false;

    @Prop
    public boolean leader = false;
}
