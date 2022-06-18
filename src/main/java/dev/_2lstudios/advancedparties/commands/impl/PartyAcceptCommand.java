package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.api.events.PartyAcceptEvent;
import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
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

        if (player.isInParty()) {
            player.sendI18nMessage("common.already-in-party");
            return;
        }

        if (partyID.length() > 16) {
            RequestStatus status = player.getPendingRequestFrom(partyID);

            if (status == RequestStatus.PENDING) {
                Party party = ctx.getPlugin().getPartyManager().getParty(partyID);

                accept(player, party);
            } else {
                player.sendI18nMessage("common.invalid-or-expired");
            }
        } else {
            RequestStatus status = player.getPendingRequestsFromByPartyOwner(partyID);

            if (status == RequestStatus.PENDING) {
                Party party = ctx.getPlugin().getPartyManager().getPartyByLeader(partyID);

                accept(player, party);
            } else {
                player.sendI18nMessage("common.invalid-or-expired");
            }
        }
    }
}
