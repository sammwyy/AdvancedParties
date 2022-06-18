package dev._2lstudios.advancedparties.messaging;

import dev._2lstudios.advancedparties.messaging.packets.PartyChatPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyDisbandPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyHookPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyInvitePacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyJoinPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyKickPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyLeavePacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyPromotePacket;
import dev._2lstudios.advancedparties.messaging.packets.PartySendPacket;
import dev._2lstudios.advancedparties.messaging.packets.PartyUpdatePacket;
import org.bukkit.configuration.ConfigurationSection;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.parties.PartyDisbandReason;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.utils.ComponentUtils;

import lib__net.md_5.bungee.api.chat.ComponentBuilder;
import lib__net.md_5.bungee.api.chat.TextComponent;

public class RedisHandler {
    private AdvancedParties plugin;

    public RedisHandler(AdvancedParties plugin) {
        this.plugin = plugin;
    }

    public void handle(PartyHookPacket packet) {
        this.plugin.getHookManager().processPacket(packet);
    }

    public void handle(PartyChatPacket packet) {
        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.getPartyID().equals(packet.getPartyID())) {
                player.sendMessage(
                    player.getI18nMessage("chat.format")
                        .replace("{player}", packet.getPlayerName())
                        .replace("{message}", packet.getMessage())
                );
            }
        }
    }

    public void handle(PartyPromotePacket packet) {
        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.getPartyID().equals(packet.getPartyID())) {
                player.sendMessage(
                  player.getI18nMessage("promote.promoted")
                    .replace("{player}", packet.getPlayerName())
                );
            }
        }
    }

    public void handle(PartyLeavePacket packet) {
        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.getPartyID().equals(packet.getPartyID())) {
                player.sendMessage(
                    player.getI18nMessage("leave.leave-notify")
                        .replace("{player}", packet.getPlayerName())
                );
            }
        }
    }

    public void handle(PartySendPacket packet) {
        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.getPartyID().equals(packet.getPartyID())) {
                player.sendToServer(packet.getServer());
            }
        }
    }

    public void handle(PartyDisbandPacket packet) {
        this.plugin.getPartyManager().delete(packet.getPartyID());

        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.getPartyID().equals(packet.getPartyID())) {
                player.setParty(null);

                if (packet.getReason() == PartyDisbandReason.BY_LEADER) {
                    player.sendI18nMessage("disband.disbanded-by-leader");
                } else if (packet.getReason() == PartyDisbandReason.TIMEOUT) {
                    player.sendI18nMessage("disband.disbanded-by-timeout");
                }
            }
        }
    }

    public void handle(PartyUpdatePacket packet) {
        Party party = this.plugin.getPartyManager().getPartyIfCached(packet.getPartyID());
        if (party != null) {
            party.sync();
        }
    }

    public void handle(PartyJoinPacket packet) {
        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.getPartyID().equals(packet.getPartyID())) {
                player.sendMessage(
                    player.getI18nMessage("accept.join-notify")
                        .replace("{player}", packet.getPlayerName())
                );
            }
        }
    }

    public void handle(PartyKickPacket packet) {
        PartyPlayer target = this.plugin.getPlayerManager().getPlayer(packet.getTargetName());

        if (target != null) {
            target.setParty(null);
            target.sendI18nMessage("kick.kick-notify");
        }

        for (PartyPlayer player : this.plugin.getPlayerManager().getPlayers()) {
            if (player.isInParty() && player.getPartyID().equals(packet.getPartyID())) {
                player.sendMessage(
                    player.getI18nMessage("kick.kick-notify-other")
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

                if (parts.length > 1) {
                    footer = parts[1];
                } else {
                    footer = "";
                }
            }

            if (footer == null) {
                target.sendMessage(header);
            } else {
                ComponentBuilder builder = new ComponentBuilder();
                builder.append(TextComponent.fromLegacyText(header));

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

                builder.append(TextComponent.fromLegacyText(footer));
                target.sendMessage(builder.create());
            }
        }
    }
}
