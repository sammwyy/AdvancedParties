package dev._2lstudios.advancedparties.interfaces.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev._2lstudios.advancedparties.interfaces.menus.PageMenu;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;

public class PageOpenerItem extends InterfaceItem {
    private InterfaceMenu menu;

    public PageOpenerItem(PageMenu menu) {
        this.menu = menu;
        setType(Material.ARROW);
        setName("Page " + menu.getPage());
    }

    @Override
    public void onClick(Player player, Inventory clickedInventory) {
        menu.build(player);
    }
}
