package net.madgamble.bennet.bungeeannouncer.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class FontFormat {

	public static BaseComponent[] translateString(String value)  {
		return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', value));
	}
}
