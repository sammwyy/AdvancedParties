package dev._2lstudios.advancedparties.interfaces.menus;

import java.util.List;

import dev._2lstudios.advancedparties.interfaces.items.PageOpenerItem;
import dev._2lstudios.advancedparties.interfaces.items.PartyHeadItem;
import dev._2lstudios.advancedparties.players.PartyPlayer;
import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;
import dev._2lstudios.interfacemaker.interfaces.contexts.MenuBuildContext;

public class PartyMenu extends PageMenu {
    private static int MAX_ENTRY_COUNT = 21;

    private PartyPlayer partyPlayer;

    public PartyMenu(PartyPlayer partyPlayer, int page) {
        super(page);
        this.partyPlayer = partyPlayer;
        setRows(6);
        setTitle(partyPlayer.formatMessage(partyPlayer.getI18nMessage("menu.title")));
    }

    @Override
    public void onBuild(MenuBuildContext context) {
        List<String> playerNames = partyPlayer.getParty().getMembers();
        InterfaceItem[] heads = new InterfaceItem[MAX_ENTRY_COUNT];
        int playerNamesSize = playerNames.size();
        int page = getPage();
        int skip = Math.max(0, ((page - 1) * MAX_ENTRY_COUNT) - 1);

        for (int i = skip; i < MAX_ENTRY_COUNT && i < playerNamesSize; i++) {
            String playerName = playerNames.get(i);
            InterfaceItem item = new PartyHeadItem(playerName);

            heads[i] = item;
        }

        context.fill(1, heads);

        if (page > 1) {
            context.setItem(45, new PageOpenerItem(new PartyMenu(partyPlayer, page - 1)));
        }

        if (playerNamesSize - (skip + 1) > MAX_ENTRY_COUNT) {
            context.setItem(53, new PageOpenerItem(new PartyMenu(partyPlayer, page + 1)));
        }
    }
}
