package dev._2lstudios.advancedparties.players;

import java.util.List;

import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.commands.CommandExecutor;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.requests.PartyRequest;
import dev._2lstudios.advancedparties.utils.PacketUtils;

import lib__net.md_5.bungee.api.chat.BaseComponent;
import lib__net.md_5.bungee.api.chat.ComponentBuilder;
import lib__net.md_5.bungee.chat.ComponentSerializer;

public class PartyPlayer extends CommandExecutor {
    private Player bukkitPlayer;
    
    private PartyPlayerData data;
    private Party party;

    public PartyPlayer(AdvancedParties plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    public String getLowerName() {
        return this.bukkitPlayer.getName().toLowerCase();
    }

    public List<PartyRequest> getPendingRequests() {
        return this.getPlugin().getRequestManager().getPendingByPlayer(this.getLowerName());
    }

    public List<PartyRequest> getRequests() {
        return this.getPlugin().getRequestManager().getRequestsForPlayer(this.getLowerName());
    }

    public boolean hasAlreadyRequestTo(String player) {
        for (PartyRequest request : this.getPendingRequests()) {
            if (request.target.equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPendingRequestForParty(String party) {
        for (PartyRequest request : this.getRequests()) {
            if (request.party.equalsIgnoreCase(party)) {
                return true;
            }
        }
        return false;
    }

    public void setParty(Party party) {
        if (party == null) {
            this.party = null;
            if (this.data != null) {
                this.data.delete();
            }
        } else {
            this.party = party;
            this.data = new PartyPlayerData();
            this.data.username = this.getLowerName();
            this.data.party = party.getID();
            this.data.save();
        }
    }

    public void createParty() {
        Party party = this.getPlugin().getPartyManager().createParty(this.getLowerName());
        this.setParty(party);
    }

    public Party getParty() {
        return this.party;
    }

    public boolean isInParty() {
        return this.party != null;
    }

    public void download() {
        this.data = this.getPlugin().getPlayerRepository().findOne(MapFactory.create("username", this.getLowerName()));

        if (this.data != null) {
            this.party = this.getPlugin().getPartyManager().getParty(this.data.party);
        }
    }

    public void sendRawMessage(String component, byte type) {
        PacketUtils.sendJSON(this.getBukkitPlayer(), component, type);
    }

    public void sendRawMessage(String component) {
        this.sendRawMessage(component, (byte) 0);
    }

    public void sendActionBar(String text) {
        this.sendRawMessage(ComponentSerializer.toString(new ComponentBuilder(text).create()), (byte) 2);
    }

    public void sendMessage(BaseComponent component) {
        this.sendRawMessage(ComponentSerializer.toString(component));
    }

    public void sendMessage(BaseComponent[] components) {
        this.sendRawMessage(ComponentSerializer.toString(components));
    }
}