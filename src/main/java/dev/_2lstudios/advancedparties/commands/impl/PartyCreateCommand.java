package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.api.events.PartyCreateEvent;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "create"
)
public class PartyCreateCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();

        if (player.isInParty()) {
            player.sendI18nMessage("common.already-in-party");
        } else {
            if (ctx.getPlugin().callEvent(new PartyCreateEvent(player))) {
                player.createParty();
                player.sendI18nMessage("create.success");
            }
        }
    }
}
