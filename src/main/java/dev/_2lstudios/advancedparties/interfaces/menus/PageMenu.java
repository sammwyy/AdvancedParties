package dev._2lstudios.advancedparties.interfaces.menus;

import dev._2lstudios.interfacemaker.interfaces.InterfaceMenu;

public class PageMenu extends InterfaceMenu {
    private int page;

    public PageMenu(int page) {
        this.page = Math.max(1, page);
    }

    public int getPage() {
        return page;
    }
}
