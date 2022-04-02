package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.messaging.packets.PartyJoinPacket;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.requests.PartyRequest;

@Command(
  name = "accept",
  arguments = { Argument.STRING },
  minArguments = 1
)
public class PartyAcceptCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        String partyID = ctx.getArguments().getString(0);

        if (player.isInParty()) {
            player.sendI18nMessage("common.already-in-party");
            return;
        } 
        
        PartyRequest request = player.getPendingRequestForParty(partyID);
        if (request != null) {
            Party party = ctx.getPlugin().getPartyManager().getParty(partyID);

            if (party == null) {
                player.sendI18nMessage("common.invalid-or-expired");
            } else {
                player.setParty(party);
                player.sendI18nMessage("accept.accepted");

                party.addMember(player);
                party.announcePlayerJoin(player.getBukkitPlayer().getName());
                

                request.delete();
            }
        } else {
            player.sendI18nMessage("common.invalid-or-expired");
        }
    }
}
