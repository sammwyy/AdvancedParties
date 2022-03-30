package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.requests.PartyRequest;

@Command(
  name = "requests"
)
public class PartyRequestsCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        for (PartyRequest request : player.getRequests()) {
            player.sendMessage(request.party);
        }
    }
}
