package dev._2lstudios.advancedparties.parties;

import java.util.List;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class PartyData extends Entity {
    @ID
    public String id;
  
    @Prop
    public String leader;

    @Prop
    public List<String> members;
}
