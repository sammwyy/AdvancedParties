package dev._2lstudios.advancedparties.interfaces.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.placeholders.Formatter;

public class PartyHeadItem extends InterfaceItem {
    private String playerName;

    public PartyHeadItem(PartyPlayer partyPlayer, String playerName) {
        this.playerName = playerName;

        Material material = Material.getMaterial("PLAYER_HEAD");
        if (material == null) {
            material = Material.getMaterial("SKULL_ITEM");
        }

        this.setType(material);
        this.setSkullOwner(playerName);
        this.setName(Formatter.format(partyPlayer.getBukkitPlayer(), partyPlayer.getI18nMessage("info.as-gui.item-name").replace("{member}", playerName)));
        this.setLore(Formatter.format(partyPlayer.getBukkitPlayer(), partyPlayer.getI18nMessage("info.as-gui.item-lore").replace("{member}", playerName)));
    }

    @Override
    public void onClick(Player player, Inventory clickedInventory) {
        player.performCommand("party kick " + playerName);
    }
}
