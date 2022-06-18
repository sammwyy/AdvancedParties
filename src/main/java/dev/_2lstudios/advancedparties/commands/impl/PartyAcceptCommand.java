package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.api.events.PartyAcceptEvent;
import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.parties.PartyManager;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.requests.RequestStatus;

@Command(
  name = "accept",
  alias = {"join"},
  arguments = { Argument.STRING },
  minArguments = 1
)
public class PartyAcceptCommand extends CommandListener {
    private void accept(PartyPlayer player, Party party) {
        if (party == null) {
            player.sendI18nMessage("common.invalid-or-expired");
        } else if (party.isMaxMembersReached()) {
            player.sendI18nMessage("accept.limit-reached");
        } else {
            PartyAcceptEvent event = new PartyAcceptEvent(party.getID(), player);

            if (this.plugin.callEvent(event)) {
                player.setParty(party);
                player.sendI18nMessage("accept.accepted");

                party.addMember(player);
                party.sendPartyUpdate();
                party.announcePlayerJoin(player.getBukkitPlayer().getName());

                this.plugin.getRequestManager().deleteRequest(party.getID(), player.getBukkitPlayer().getName());
            }
        }
    }

    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        String partyID = ctx.getArguments().getString(0);
        InviteAccepting accepting = getInviteAccepting(ctx, partyID);
        Party party = accepting.getPartyInstance(partyID);

        if (player.isInParty()) {
            player.sendI18nMessage("common.already-in-party");
            return;
        }

        if (party == null) {
            player.sendI18nMessage("common.invalid-or-expired");
            return;
        }

        if (party.isOpen()) {
            accept(player, party);
            return;
        }

        if (accepting.hasPendingInvite(partyID, player))
            accept(player, party);
        else
            player.sendI18nMessage("common.invalid-or-expired");
    }

    private InviteAccepting getInviteAccepting(CommandContext ctx, String partyID) {
        PartyManager partyManager = ctx.getPlugin().getPartyManager();

        if (partyID.length() > 16)
            return new PartyIdInviteAccepting(partyManager);
        else
            return new PlayerNameInviteAccepting(partyManager);
    }

    interface InviteAccepting {
        boolean hasPendingInvite(String partyID, PartyPlayer player);

        Party getPartyInstance(String partyID);
    }

    class PartyIdInviteAccepting implements InviteAccepting {
        private PartyManager partyManager;

        public PartyIdInviteAccepting(PartyManager partyManager) {
            this.partyManager = partyManager;
        }

        @Override
        public boolean hasPendingInvite(String partyID, PartyPlayer player) {
            return player.getPendingRequestFrom(partyID) == RequestStatus.PENDING;
        }

        @Override
        public Party getPartyInstance(String partyID) {
            return partyManager.lookupParty(partyID);
        }
    }

    class PlayerNameInviteAccepting implements InviteAccepting {

        private PartyManager partyManager;

        public PlayerNameInviteAccepting(PartyManager partyManager) {
            this.partyManager = partyManager;
        }

        @Override
        public boolean hasPendingInvite(String partyID, PartyPlayer player) {
            return player.getPendingRequestsFromByPartyOwner(partyID) == RequestStatus.PENDING;
        }

        @Override
        public Party getPartyInstance(String partyID) {
            return partyManager.getPartyByLeader(partyID);
        }
    }
}
