package dev._2lstudios.advancedparties.requests;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class PartyRequest extends Entity {
    @ID
    public String id;

    @Prop
    public String party;

    @Prop
    public String source;

    @Prop
    public String target;

    @Prop
    public long timestamp;

    public long getTimeAgo() {
        return System.currentTimeMillis() - this.timestamp;
    }
}