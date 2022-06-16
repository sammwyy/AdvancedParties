package dev._2lstudios.advancedparties.players;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.dotphin.milkshake.find.FindFilter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.commands.CommandExecutor;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.requests.RequestStatus;
import dev._2lstudios.advancedparties.utils.PacketUtils;
import dev._2lstudios.advancedparties.utils.PlayerUtils;
import dev._2lstudios.advancedparties.utils.ServerUtils;

import lib__net.md_5.bungee.api.chat.BaseComponent;
import lib__net.md_5.bungee.api.chat.ComponentBuilder;
import lib__net.md_5.bungee.chat.ComponentSerializer;

import me.clip.placeholderapi.PlaceholderAPI;

public class PartyPlayer extends CommandExecutor {
    private Player bukkitPlayer;

    private PartyPlayerData data;
    private String partyId;

    public PartyPlayer(AdvancedParties plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    public String getName() {
        return this.bukkitPlayer.getName();
    }

    public String getLowerName() {
        return this.getName().toLowerCase();
    }

    public RequestStatus getRequestTo(String player) {
        return this.getPlugin().getRequestManager().getRequest(player, this.getPartyID());
    }

    public RequestStatus getPendingRequestFrom(String party) {
        return this.getPlugin().getRequestManager().getRequest(this, party);
    }

    public void setParty(Party party) {
        if (party == null) {
            this.partyId = null;
            if (this.data != null) {
                this.data.delete();
            }
        } else {
            this.partyId = party.getID();
            this.data = new PartyPlayerData();
            this.data.username = this.getLowerName();
            this.data.party = this.partyId;
            if (party.isLeader(this)) {
                this.data.leader = true;
            }
            this.data.save();
        }
    }

    public void createParty() {
        Party party = this.getPlugin().getPartyManager().createParty(this.getLowerName());
        this.setParty(party);
    }

    public Party getParty() {
        if (this.partyId == null) {
            return null;
        } else {
            return this.getPlugin().getPartyManager().getParty(this.partyId);
        }
    }

    public String getPartyID() {
        return this.partyId;
    }

    public boolean isInParty() {
        return this.partyId != null;
    }

    public boolean isPartyLeader() {
        return this.data.leader;
    }

    public boolean getPartyChat() {
        return this.data.partyChat;
    }

    public void setPartyChat(boolean result) {
        this.data.partyChat = result;
        this.data.save();
    }

    public void download() {
        this.data = this.getPlugin().getPlayerRepository().findOne(new FindFilter("username", this.getLowerName()));

        if (this.data != null) {
            this.partyId = this.data.party;
        }
    }

    public void sendRawMessage(String component, byte type) {
        if (ServerUtils.hasChatComponentAPI()) {
            this.bukkitPlayer.spigot().sendMessage(net.md_5.bungee.chat.ComponentSerializer.parse(component));
        } else {
            PacketUtils.sendJSON(this.getBukkitPlayer(), component, type);
        }
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

    public void sendToServer(String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            this.getBukkitPlayer().sendPluginMessage(this.getPlugin(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception e) {
            this.getBukkitPlayer().sendMessage(ChatColor.RED + "Error when trying to connect to " + server);
        }
    }

    @Override
    public String formatMessage(String message) {
        String output = super.formatMessage(message);

        if (this.getPlugin().hasPlugin("PlaceholderAPI")) {
            output = PlaceholderAPI.setPlaceholders(this.getBukkitPlayer(), output);
        }

        return output;
    }

    @Override
    public String getLang() {
        String lang = null;

        if (ServerUtils.hasPlayerGetLocaleAPI()) {
            lang = this.getBukkitPlayer().getLocale();
        } else {
            lang = PlayerUtils.getPlayerLocaleInLegacyWay(this.bukkitPlayer);
        }

        return lang == null ? super.getLang() : lang;
    }

    public RequestStatus getPendingRequestsFromByPartyOwner(String partyOwner) {
        return this.getPlugin().getRequestManager().getRequestByLeader(this, partyOwner);
    }
}
