package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.OfflinePlayer;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "invite",
  arguments = { Argument.STRING },
  minArguments = 1
)
public class PartyInviteCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        AdvancedParties plugin = ctx.getPlugin();
        PartyPlayer player = ctx.getPlayer();
        Party party = player.getParty();
        String targetName = ctx.getArguments().getString(0);

        if (party != null) {
            OfflinePlayer target = new OfflinePlayer(plugin, targetName);
            target.download();
            
            if (target.isInParty()) {
                player.sendI18nMessage("invite.target-already-in-party");
            } else {
                player.sendMessage(
                    player.getI18nMessage("invite.sent")
                        .replace("{target}", targetName)
                );
                plugin.getRequestManager().createRequest(party, targetName);
            }
        } else {
            player.sendI18nMessage("common.not-in-party");
        }
    }
}
