package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.requests.RequestStatus;

@Command(
  name = "deny",
  arguments = { Argument.STRING },
  minArguments = 1
)
public class PartyDenyCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        String partyID = ctx.getArguments().getString(0);

        RequestStatus status = player.getPendingRequestFrom(partyID);

        if (status == RequestStatus.PENDING) {
            Party party = ctx.getPlugin().getPartyManager().getParty(partyID);

            if (party == null) {
                player.sendI18nMessage("common.invalid-or-expired");
            } else {
                player.sendI18nMessage("deny.denied");
                ctx.getPlugin().getRequestManager().denyRequest(partyID, player.getName());
            }
        } else {
            player.sendI18nMessage("common.invalid-or-expired");
        }
    }
}
