package fr.kap35.kapeasymenu.debug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Debug {

    public Debug() {}

    public final String getPrefixPlugin() {
        return ChatColor.DARK_BLUE + "[KapEasyMenu] " + ChatColor.RESET;
    }

    public final String getErrorAddingMenu() {
        return getPrefixPlugin() + ChatColor.RED + "Error adding menu : ";
    }

    public void printErrorAddingMenu(String error) {
        Bukkit.getConsoleSender().sendMessage(getErrorAddingMenu() + error);
    }

    public void printErrorMenuNotFound(String menuName) {
        Bukkit.getConsoleSender().sendMessage(getPrefixPlugin() + ChatColor.RED + "Menu " + menuName + " not found !");
    }

    public void printErrorOpeningMenu(String error) {
        Bukkit.getConsoleSender().sendMessage(getPrefixPlugin() + ChatColor.RED + "Error opening menu : " + error);
    }

}
