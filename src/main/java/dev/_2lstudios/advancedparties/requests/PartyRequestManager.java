package dev._2lstudios.advancedparties.requests;

import java.util.ArrayList;
import java.util.List;

import com.dotphin.milkshakeorm.utils.MapFactory;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.parties.Party;

public class PartyRequestManager {
    private AdvancedParties plugin;
    private List<PartyRequest> queued;
    
    public PartyRequestManager(AdvancedParties plugin) {
        this.plugin = plugin;
        this.queued = new ArrayList<>();
    }

    public void handleExpiration() {
        for(PartyRequest request : this.queued) {
            if (request.getTimeAgo() > this.plugin.getConfig().getInt("requests.requests") * 1000) {
                request.delete();
            }
        }
    }

    public PartyRequest createRequest(Party party, String target) {
        PartyRequest request = new PartyRequest();
        request.party = party.getID();
        request.source = party.getLeader().toLowerCase();
        request.target = target.toLowerCase();
        request.timestamp = System.currentTimeMillis();
        request.save();

        this.queued.add(request);
        return request;
    }

    public List<PartyRequest> getPendingByPlayer(String player) {
        List<PartyRequest> requests = new ArrayList<>();
        for (PartyRequest request : this.plugin.getRequestsRepository().findMany(MapFactory.create("source", player.toLowerCase()))) {
            if (request.getTimeAgo() > this.plugin.getConfig().getInt("requests.expiration") * 1000) {
                request.delete();
            } else {
                requests.add(request);
            }
        }
        return requests;
    }

    public List<PartyRequest> getRequestsForPlayer(String player) {
        List<PartyRequest> requests = new ArrayList<>();
        for (PartyRequest request : this.plugin.getRequestsRepository().findMany(MapFactory.create("target", player.toLowerCase()))) {
            if (request.getTimeAgo() > this.plugin.getConfig().getInt("requests.expiration") * 1000) {
                request.delete();
            } else {
                requests.add(request);
            }
        }
        return requests;
    }
}