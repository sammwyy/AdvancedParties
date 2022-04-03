package dev._2lstudios.advancedparties.parties;

import java.util.ArrayList;
import java.util.List;

import com.dotphin.milkshakeorm.utils.MapFactory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.PartyDisbandPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyJoinPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyUpdatePacket;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class Party {
    private AdvancedParties plugin;
    private PartyData data;
    private int disbandCount = 0;

    public Party(AdvancedParties plugin, PartyData data) {
        this.plugin = plugin;
        this.data = data;
    }

    public void disband() {
        for (PartyPlayer player : this.getPlayers()) {
            player.setParty(null);
        }

        this.plugin.getPlayerRepository().deleteMany(MapFactory.create("party", this.getID()));
        this.data.delete();
        this.plugin.getPubSub().publish(new PartyDisbandPacket(this.getID()));
    }

    public void removeMember(String player) {
        this.data.members.remove(player.toLowerCase());
        this.data.save();
    }

    public void removeMember(PartyPlayer player) {
        this.removeMember(player.getBukkitPlayer().getName());
    }

    public void addMember(String player) {
        this.data.members.add(player.toLowerCase());
        this.data.save();
    }

    public void addMember(PartyPlayer player) {
        this.addMember(player.getBukkitPlayer().getName());
    }

    public int getDisbandCount() {
        return this.disbandCount;
    }

    public int bumpDisbandCount() {
        this.disbandCount++;
        return this.disbandCount;
    }

    public String getID() {
        return this.data.id;
    }

    public String getLeader() {
        return this.data.leader;
    }

    public List<String> getMembers() {
        return this.data.members;
    }

    public List<PartyPlayer> getPlayers() {
        List<PartyPlayer> result = new ArrayList<>();

        for (String member : this.data.members) {
            Player bukkitPlayer = Bukkit.getPlayerExact(member);
            if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
                result.add(this.plugin.getPlayerManager().getPlayer(bukkitPlayer));
            }
        }

        return result;
    }

    public boolean isLeader(PartyPlayer player) {
        return player.getLowerName().equals(this.getLeader());
    }

    public void announcePlayerJoin(String playerName) {
        this.plugin.getPubSub().publish(new PartyJoinPacket(playerName, this.getID()));
    }

    public boolean isMaxMembersReached() {
        return this.getMembers().size() >= this.plugin.getConfig().getInt("parties.max-members");
    }

    public void sendPartyUpdate() {
        this.plugin.getPubSub().publish(new PartyUpdatePacket(this.getID()));
    }

    public void sync() {
        this.data.refresh();
    }
}