package dev._2lstudios.advancedparties.interfaces.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;

public class PartyHeadItem extends InterfaceItem {
    public PartyHeadItem(String playerName) {
        setType(Material.PLAYER_HEAD);
        setName(ChatColor.GREEN + playerName);
    }
}
