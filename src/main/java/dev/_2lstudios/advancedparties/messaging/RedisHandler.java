package dev._2lstudios.advancedparties.messaging;

import dev._2lstudios.advancedparties.AdvancedParties;
import dev._2lstudios.advancedparties.messaging.packets.PartyInvitePacket;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.advancedparties.utils.ComponentUtils;

import lib__net.md_5.bungee.api.chat.ComponentBuilder;

public class RedisHandler {
    private AdvancedParties plugin;

    public RedisHandler(AdvancedParties plugin) {
        this.plugin = plugin;
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
                builder.append(ComponentUtils.createClickeableText(
                    target.formatMessage(target.getI18nMessage("common.accept")),
                    "/party accept " + packet.getPartyID()
                ));
                builder.append(" ");
                builder.append(ComponentUtils.createClickeableText(
                    target.formatMessage(target.getI18nMessage("common.deny")),
                    "/party deny " + packet.getPartyID()
                ));
                builder.append(footer);
                target.sendMessage(builder.create());
            }
        }
    }
}
