package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "chat"
)
public class PartyChatCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();

        if (player.isInParty()) {
            player.setPartyChat(!player.getPartyChat());
            if (player.getPartyChat()) {
                player.sendI18nMessage("chat.enabled");
            } else {
                player.sendI18nMessage("chat.disabled");
            }
        } else {
            player.sendI18nMessage("common.not-in-party");
        }
    }
}
