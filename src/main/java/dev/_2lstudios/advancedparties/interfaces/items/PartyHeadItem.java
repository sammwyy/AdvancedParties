package dev._2lstudios.advancedparties.interfaces.items;

import org.bukkit.Material;

import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class PartyHeadItem extends InterfaceItem {
    public PartyHeadItem(PartyPlayer partyPlayer, String playerName) {
        setType(Material.PLAYER_HEAD);
        setName(Formatter.format(partyPlayer.getBukkitPlayer(), partyPlayer.getI18nMessage("menu.item-name").replace("%name%", playerName)));
        setLore(Formatter.format(partyPlayer.getBukkitPlayer(), partyPlayer.getI18nMessage("menu.item-lore").replace("%name%", playerName)));
    }
}
