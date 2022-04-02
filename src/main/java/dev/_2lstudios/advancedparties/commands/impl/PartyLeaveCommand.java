package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "leave"
)
public class PartyLeaveCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        
        if (player.isInParty()) {
            Party party = player.getParty();
            party.removeMember(player);
            player.setParty(null);
            player.sendI18nMessage("leave.leaved");
        } else {
            player.sendI18nMessage("common.not-in-party");
        }
    }
}
