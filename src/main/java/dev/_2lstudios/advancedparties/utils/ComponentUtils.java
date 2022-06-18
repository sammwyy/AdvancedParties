package dev._2lstudios.advancedparties.utils;

import lib__net.md_5.bungee.api.chat.*;

public class ComponentUtils {
    public static BaseComponent[] createClickeableText(String text, String command) {
        return new ComponentBuilder()
          .append(text)
          .event(
            new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(text))
          )
          .event(
            new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
          ).create();
    }
}
