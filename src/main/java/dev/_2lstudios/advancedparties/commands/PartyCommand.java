package dev._2lstudios.advancedparties.commands;

@Command(
  name = "party"
)
public class PartyCommand extends CommandListener {
  @Override
  public void onExecuteByPlayer(CommandContext ctx) {
    ctx.getPlayer().sendI18nMessage("test");
  }
}
