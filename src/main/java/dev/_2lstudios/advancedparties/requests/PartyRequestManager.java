package dev._2lstudios.advancedparties.requests;

import com.dotphin.milkshake.find.FindFilter;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.parties.PartyData;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class PartyRequestManager {
    private AdvancedParties plugin;

    public PartyRequestManager(AdvancedParties plugin) {
        this.plugin = plugin;
    }

    public RequestStatus getRequest(String player, String party) {
        String raw = this.plugin.getCache().get(player.toLowerCase() + "_" + party);
        return raw == null ? RequestStatus.NONE : RequestStatus.valueOf(raw.toUpperCase());
    }

    public RequestStatus getRequest(PartyPlayer player, String party) {
        return this.getRequest(player.getBukkitPlayer().getName(), party);
    }

    public void deleteRequest(String party, String target) {
        this.plugin.getCache().delete(target.toLowerCase() + "_" + party);
    }

    public void denyRequest(String party, String target) {
        this.plugin.getCache().set(
            target.toLowerCase() + "_" + party,
            this.plugin.getConfig().getInt("requests.cooldown-after-deny"),
            "denied"
        );
    }

    public void createRequest(String party, String target) {
        this.plugin.getCache().set(
            target.toLowerCase() + "_" + party,
            this.plugin.getConfig().getInt("requests.expiration"),
            "pending"
        );
    }

    public void createRequest(Party party, String target) {
        this.createRequest(party.getID(), target);
    }

    public RequestStatus getRequestByLeader(PartyPlayer player, String leader) {
        PartyData partyData = plugin.getPartyRepository().findOne(new FindFilter("leader", leader.toLowerCase()));

        if (partyData == null)
            return RequestStatus.NONE;

        return getRequest(player, partyData.getID());
    }
}
