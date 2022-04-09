package dev._2lstudios.advancedparties.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.errors.BadArgumentException;
import dev._2lstudios.advancedparties.errors.PlayerOfflineException;
import dev._2lstudios.advancedparties.players.PartyPlayer;

public class CommandArguments {
    private AdvancedParties plugin;
    private Argument[] requiredArguments;
    private boolean isParsingLargeString = false;
    
    private List<Object> arguments = new ArrayList<>();

    public CommandArguments(AdvancedParties plugin, Argument[] requiredArguments) {
        this.plugin = plugin;
        this.requiredArguments = requiredArguments;
    }

    public boolean hasIndex(int index) {
        return index < arguments.size() && index >= 0;
    }

    public String getString(int index) {
        if (!this.hasIndex(index)) {
            return null;
        }

        return (String) this.arguments.get(index);
    }

    public int getInt(int index) {
        if (!this.hasIndex(index)) {
            return 0;
        }

        return (int) this.arguments.get(index);
    }

    public boolean getBoolean(int index) {
        if (!this.hasIndex(index)) {
            return false;
        }

        return (boolean) this.arguments.get(index);
    }

    public PartyPlayer getPlayer(int index) {
        if (!this.hasIndex(index)) {
            return null;
        }

        return (PartyPlayer) this.arguments.get(index);
    }

    public void parse(String[] args) throws BadArgumentException, PlayerOfflineException {
        int i = 0;

        for (String arg : args) {
            if (this.requiredArguments.length <= i) {
                if (isParsingLargeString) {
                    int index = this.arguments.size() - 1;
                    String str = (String) this.arguments.get(index);
                    str += " " + arg;
                    this.arguments.set(index, str);
                } else {
                    this.arguments.add(arg);
                }
                continue;
            }

            Argument type = this.requiredArguments[i];
            Object value = null;

            if (type == Argument.LARGE_STRING)
            {
                isParsingLargeString = true;
                value = arg;
            } 
            
            else if (type == Argument.BOOL) {
                if (arg.equalsIgnoreCase("true")) {
                    value = true;
                } else if (arg.equalsIgnoreCase("false")) {
                    value = false;
                }
                throw new BadArgumentException(arg, "boolean");
            }

            else if (type == Argument.INT) {
                try {
                    value = Integer.parseInt(arg);
                } catch (Exception _exception) {
                    throw new BadArgumentException(arg, "number");
                }
            }

            else if (type == Argument.ONLINE_PLAYER) {
                Player player = Bukkit.getServer().getPlayerExact(arg);
                if (player.isOnline()) {
                    value = this.plugin.getPlayerManager().getPlayer(player);
                } else {
                    throw new PlayerOfflineException(arg);
                }
            }

            else if (type == Argument.STRING) {
                value = arg;
            }

            i++;
            this.arguments.add(value);
        }
    }
}
