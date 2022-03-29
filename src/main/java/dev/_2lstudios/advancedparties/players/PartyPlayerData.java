package dev._2lstudios.advancedparties.players;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class PartyPlayerData extends Entity {
    @ID
    public String id;

    @Prop
    public String username;

    @Prop
    public String party;

    @Prop
    public boolean partyChat = false;
}
