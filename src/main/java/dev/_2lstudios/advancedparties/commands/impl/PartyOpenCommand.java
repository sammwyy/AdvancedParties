package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "open"
)
public class PartyOpenCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        Party party = player.getParty();

        if (party == null) {
            player.sendI18nMessage("common.not-in-party");
            return;
        }

        if (!party.isLeader(player.getName())){
            player.sendI18nMessage("open.not-leader");
            return;
        }

        party.setOpen(!party.isOpen());
        player.sendI18nMessage(party.isOpen() ? "open.enabled" : "open.disabled");
    }
}
