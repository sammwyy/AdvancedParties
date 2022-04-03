package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.messaging.packets.PartyInvitePacket;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.players.OfflinePlayer;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.requests.PartyRequest;

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
            if (!party.isLeader(player)) {
                player.sendI18nMessage("invite.not-leader");
            } else if (party.isMaxMembersReached()) {
                player.sendI18nMessage("invite.limit-reached");
            } else if (player.hasAlreadyRequestTo(targetName)) {
                player.sendI18nMessage("invite.already-pending");
            } else {
                OfflinePlayer target = new OfflinePlayer(plugin, targetName);
                target.download();
                
                if (target.isInParty()) {
                    player.sendI18nMessage("invite.target-already-in-party");
                } else {
                    player.sendMessage(
                        player.getI18nMessage("invite.sent")
                            .replace("{target}", targetName)
                    );
    
                    PartyRequest request = plugin.getRequestManager().createRequest(party, targetName);
                    PartyInvitePacket packet = new PartyInvitePacket(request);
                    plugin.getPubSub().publish(packet);
                }
            }
        } else {
            player.sendI18nMessage("common.not-in-party");
        }
    }
}
