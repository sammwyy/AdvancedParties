package dev._2lstudios.advancedparties.messaging;

import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.PartyInvitePacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyKickPacket;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.utils.ComponentUtils;

import lib__net.md_5.bungee.api.chat.ComponentBuilder;

public class RedisHandler {
    private AdvancedParties plugin;

    public RedisHandler(AdvancedParties plugin) {
        this.plugin = plugin;
    }

    public void handle(PartyKickPacket packet) {
        PartyPlayer target = this.plugin.getPlayerManager().getPlayer(packet.getTargetName());

        if (target != null) {
            target.setParty(null);
            target.sendI18nMessage("kick.kick-notify");
        }

        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.getParty().getID().equals(packet.getPartyID())) {
                player.sendMessage(
                    player.getI18nMessage("kick-notify-other")
                        .replace("{player}", packet.getTargetName())  
                );
            }
        }
    }

    public void handle(PartyInvitePacket packet) {
        PartyPlayer target = this.plugin.getPlayerManager().getPlayer(packet.getTargetName());

        if (target != null) {
            String header = target.formatMessage(target.getI18nMessage("invite.received")).replace("{player}", packet.getSourceName());
            String footer = null;

            if (header.contains("{actions}")) {
                String[] parts = header.split("\\{actions\\}");
                header = parts[0];
                footer = parts[1];
            }

            if (footer == null) {
                target.sendMessage(header);
            } else {
                ComponentBuilder builder = new ComponentBuilder();
                builder.append(header);

                ConfigurationSection section = plugin.getConfig().getConfigurationSection("requests.actions");
                for (String key : section.getKeys(false)) {
                    String[] parts = section.getString(key + ".text").split(":");

                    String text = null;
                    String command = section.getString(key + ".command")
                        .replace("{party}", packet.getPartyID())
                        .replace("{source}", packet.getSourceName())
                        .replace("{player}", packet.getTargetName());

                    if (parts[0].equalsIgnoreCase("i18n")) {
                        text = target.getI18nMessage(parts[1]);
                    } else if (parts[0].equalsIgnoreCase("text")) {
                        text = parts[1];
                    }

                    builder.append(ComponentUtils.createClickeableText(target.formatMessage(text), command));
                    builder.append(" ");
                }
                
                builder.append(footer);
                target.sendMessage(builder.create());
            }
        }
    }
}
