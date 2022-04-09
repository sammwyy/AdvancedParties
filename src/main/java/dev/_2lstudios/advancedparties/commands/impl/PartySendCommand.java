package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.api.events.PartySendEvent;
import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "send",
  arguments = { Argument.STRING },
  minArguments = 1
)
public class PartySendCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();

        Party party = player.getParty();
        String server = ctx.getArguments().getString(0);

        if (party != null) {
            if (!party.isLeader(player)) {
                player.sendI18nMessage("send.not-leader");
            } else {
                PartySendEvent event = new PartySendEvent(party, player, server);
                if (ctx.getPlugin().callEvent(event)) {
                    player.sendMessage(
                    player.getI18nMessage("send.sending")
                        .replace("{server}", server)
                );
                    party.sendToServer(server);
                }
            }
        } else {
            player.sendI18nMessage("common.not-in-party");
        }
    }
}
