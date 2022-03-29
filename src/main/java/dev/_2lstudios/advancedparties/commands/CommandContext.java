package dev._2lstudios.advancedparties.commands;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.errors.BadArgumentException;
import dev._2lstudios.advancedparties.errors.PlayerOfflineException;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class CommandContext {
    private AdvancedParties plugin;
    private CommandExecutor executor;
    private CommandArguments arguments;

    public CommandContext(AdvancedParties plugin, CommandSender sender, Argument[] requiredArguments) {
        if (sender instanceof Player) {
            this.executor = plugin.getPlayerManager().getPlayer((Player) sender);
        } else {
            this.executor = new CommandExecutor(plugin, sender);
        }

        this.plugin = plugin;
        this.arguments = new CommandArguments(plugin, requiredArguments);
    }

    public void parseArguments(String[] args) throws BadArgumentException, PlayerOfflineException {
        this.arguments.parse(args);
    }

    public AdvancedParties getPlugin() {
        return this.plugin;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public PartyPlayer getPlayer() {
        return (PartyPlayer) this.executor;
    }

    public boolean isPlayer() {
        return this.executor.isPlayer();
    }

    public CommandArguments getArguments() {
        return this.arguments;
    }
}