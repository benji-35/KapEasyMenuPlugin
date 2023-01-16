package fr.kap35.kapeasymenu;

import fr.kap35.kapeasymenu.Menu.GuiManager;
import fr.kap35.kapeasymenu.debug.Debug;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class KapEasyMenu extends JavaPlugin {

    GuiManager guiManager;

    @Override
    public void onEnable() {
        guiManager = new GuiManager();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[KapEasyMenu] " + ChatColor.GREEN + "Plugin enabled !");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[KapEasyMenu] " + ChatColor.RED + "Plugin disable !");
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
