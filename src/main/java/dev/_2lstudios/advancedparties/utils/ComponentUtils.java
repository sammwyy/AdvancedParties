package dev._2lstudios.advancedparties.utils;

import lib__net.md_5.bungee.api.chat.BaseComponent;
import lib__net.md_5.bungee.api.chat.ClickEvent;
import lib__net.md_5.bungee.api.chat.ComponentBuilder;

public class ComponentUtils {
    public static BaseComponent[] createClickeableText(String text, String command) {
        return new ComponentBuilder(text)
            .event(
                new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
            ).create();
    }
}
