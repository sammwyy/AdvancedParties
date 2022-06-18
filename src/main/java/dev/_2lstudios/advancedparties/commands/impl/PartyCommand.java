package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Argument;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "party",
  arguments = { Argument.STRING }
)
public class PartyCommand extends CommandListener {
  public PartyCommand() {
    this.addSubcommand(new PartyAcceptCommand());
    this.addSubcommand(new PartyChatCommand());
    this.addSubcommand(new PartyCreateCommand());
    this.addSubcommand(new PartyDenyCommand());
    this.addSubcommand(new PartyDisbandCommand());
    this.addSubcommand(new PartyInfoCommand());
    this.addSubcommand(new PartyInviteCommand());
    this.addSubcommand(new PartyKickCommand());
    this.addSubcommand(new PartyLeaveCommand());
    this.addSubcommand(new PartySendCommand());
    this.addSubcommand(new PartyOpenCommand());
    this.addSubcommand(new PartyWarpCommand());
  }

  @Override
  public void onExecute(CommandContext ctx) {
    PartyPlayer player = ctx.getPlayer();
    String playerName = ctx.getArguments().getString(0);

    if (playerName == null) {
      ctx.getExecutor().sendI18nMessage("help");
    } else {
      if (!player.isInParty()) {
        player.createParty();
      }

     player.getBukkitPlayer().chat(("/party invite " + playerName));
    }
  }
}
