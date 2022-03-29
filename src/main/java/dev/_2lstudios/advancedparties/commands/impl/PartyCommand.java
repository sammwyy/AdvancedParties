package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;

@Command(
  name = "party"
)
public class PartyCommand extends CommandListener {
  public PartyCommand() {
    this.addSubcommand(new PartyCreateCommand());
    this.addSubcommand(new PartyDisbandCommand());
    this.addSubcommand(new PartyInfoCommand());
  }

  @Override
  public void onExecute(CommandContext ctx) {
    ctx.getExecutor().sendI18nMessage("help");
  }
}
