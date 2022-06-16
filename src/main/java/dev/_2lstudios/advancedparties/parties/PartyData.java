package dev._2lstudios.advancedparties.parties;

import java.util.List;

import com.dotphin.classserializer.annotations.Prop;
import com.dotphin.milkshake.Entity;

public class PartyData extends Entity {
    @Prop
    public String leader;

    @Prop
    public List<String> members;

    @Prop
    public boolean open;
}
