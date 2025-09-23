package br.com.rick.dungeon.util;

import org.bukkit.ChatColor;

public class MessageUtil {

    public static String translateAlternateColorCodes(String msg) {
        return ChatColor.translateAlternateColorCodes('&', "&4[&fDungeons&4]&f: " + msg);
    }
}
