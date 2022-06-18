package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.api.events.PartyKickEvent;
import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.messaging.packets.PartyKickPacket;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "kick",
  arguments = { Argument.STRING },
  minArguments = 1
)
public class PartyKickCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        String target = ctx.getArguments().getString(0);
        Party party = player.getParty();

        if (party == null) {
            player.sendI18nMessage("common.not-in-party");
            return;
        }

        if (!party.isLeader(player)) {
            player.sendI18nMessage("kick.not-leader");
            return;
        }

        if (player.getName().equalsIgnoreCase(target)) {
            player.sendI18nMessage("kick.cannot-your-self");
            return;
        }

        if (!party.hasMember(player)) {
            player.sendI18nMessage("kick.not-in-your-party");
            return;
        }

        PartyKickEvent event = new PartyKickEvent(party, player, target);

        if (ctx.getPlugin().callEvent(event)) {
            String memberRemoved = party.removeMember(target);
            if (memberRemoved != null) {
                party.sendPartyUpdate();
                player.sendMessage(
                        player.getI18nMessage("kick.kicked")
                                .replace("{player}", memberRemoved)
                );
                ctx.getPlugin().getPubSub().publish(new PartyKickPacket(party.getID(), memberRemoved));
            }
        }
    }
}
