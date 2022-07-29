package dev._2lstudios.advancedparties.parties;

import com.dotphin.milkshake.find.FindFilter;
import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.*;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Party {
    private AdvancedParties plugin;
    private PartyData data;

    public Party(AdvancedParties plugin, PartyData data) {
        this.plugin = plugin;
        this.data = data;
    }

    public void disband(PartyDisbandReason reason) {
        PartyDisbandPacket packet = new PartyDisbandPacket(this.getID(), reason);

        this.plugin.getPlayerRepository().deleteMany(new FindFilter("party", this.getID()));
        this.data.delete();
        this.plugin.getPubSub().publish(packet);
    }

    public boolean hasMember(String player) {
        Iterator<String> iterator = this.data.members.iterator();
        while (iterator.hasNext()) {
            String member = iterator.next();

            if (member.equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMember(PartyPlayer player) {
        return this.hasMember(player.getBukkitPlayer().getName());
    }

    public String removeMember(String player) {
        Iterator<String> iterator = this.data.members.iterator();
        String member = null;

        while (iterator.hasNext()) {
            member = iterator.next();

            if (member.equalsIgnoreCase(player)) {
                iterator.remove();
                break;
            }
        }

        this.data.save();
        return member;
    }

    public String removeMember(PartyPlayer player) {
        return this.removeMember(player.getBukkitPlayer().getName());
    }

    public void addMember(String player) {
        this.data.members.add(player);
        this.data.save();
    }

    public void addMember(PartyPlayer player) {
        this.addMember(player.getBukkitPlayer().getName());
    }

    public String getID() {
        return this.data.getID();
    }

    public String getLeader() {
        return this.data.leader;
    }

    public List<String> getMembers() {
        return this.data.members;
    }

    public String getMembersAsString() {
        String result = "";

        for (String member : this.getMembers()) {
            if (result != "") {
                result += ", ";
            }

            result += member;
        }

        return result;
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

    public boolean isLeader(String playerName) {
        return this.getLeader().equalsIgnoreCase(playerName);
    }

    public boolean isLeader(PartyPlayer player) {
        return this.isLeader(player.getName());
    }

    public void announcePlayerJoin(String playerName) {
        this.plugin.getPubSub().publish(new PartyJoinPacket(playerName, this.getID()));
    }

    public void announcePlayerLeave(String playerName) {
        this.plugin.getPubSub().publish(new PartyLeavePacket(playerName, this.getID()));
    }

    public int getMembersCount() {
        return this.getMembers().size();
    }

    public int getMaxMembers() {
        return this.plugin.getConfig().getInt("parties.max-members");
    }

    public boolean isMaxMembersReached() {
        return this.getMembersCount() >= this.getMaxMembers();
    }

    public boolean isOpen() {
        return this.data.open;
    }

    public void setOpen(boolean b) {
        this.data.open = b;
        this.data.save();
    }

    public void sendPartyUpdate() {
        this.plugin.getPubSub().publish(new PartyUpdatePacket(this.getID()));
    }

    public void sync() {
        this.data.refresh();
    }

    public void sendToServer(String server) {
        this.plugin.getPubSub().publish(new PartySendPacket(this.getID(), server));
    }
}
