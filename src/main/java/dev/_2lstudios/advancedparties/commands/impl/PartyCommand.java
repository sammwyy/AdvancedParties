package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;

@Command(
  name = "party"
)
public class PartyCommand extends CommandListener {
  public PartyCommand() {
    this.addSubcommand(new PartyAcceptCommand());
    this.addSubcommand(new PartyCreateCommand());
    this.addSubcommand(new PartyDenyCommand());
    this.addSubcommand(new PartyDisbandCommand());
    this.addSubcommand(new PartyInfoCommand());
    this.addSubcommand(new PartyInviteCommand());
    this.addSubcommand(new PartyKickCommand());
    this.addSubcommand(new PartyLeaveCommand());
    this.addSubcommand(new PartySendCommand());
  }

  @Override
  public void onExecute(CommandContext ctx) {
    ctx.getExecutor().sendI18nMessage("help");
  }
}
