package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.api.events.PartySendEvent;
import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;

@Command(
  name = "warp",
  arguments = {Argument.STRING}
)
public class PartyWarpCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        Party party = player.getParty();

        if (party == null) {
            player.sendI18nMessage("common.not-in-party");
            return;
        }

        if (!party.isLeader(player)){
            player.sendI18nMessage("warp.not-leader");
            return;
        }

        BungeeChannelApi channelApi = ctx.getPlugin().getBungeeChannelApi();

        player.sendMessage(player.getI18nMessage("warp.finding-server"));

        channelApi.getServer().thenAccept(server -> {
            PartySendEvent event = new PartySendEvent(party, player, server);
            if (ctx.getPlugin().callEvent(event)) {
                player.sendMessage(
                  player.getI18nMessage("warp.sending")
                    .replace("{server}", server)
                );
                party.sendToServer(server);
            }
        });
    }
}
