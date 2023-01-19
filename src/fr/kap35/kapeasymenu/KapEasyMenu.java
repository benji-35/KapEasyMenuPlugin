package fr.kap35.kapeasymenu;

import fr.kap35.kapeasymenu.Menu.GuiManager;
import fr.kap35.kapeasymenu.commands.CheckMenuExistsCommand;
import fr.kap35.kapeasymenu.commands.ListMenusCommand;
import fr.kap35.kapeasymenu.commands.OpenMenuCommand;
import fr.kap35.kapeasymenu.listeners.InventoryAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class KapEasyMenu extends JavaPlugin {

    GuiManager guiManager;

    @Override
    public void onEnable() {
        guiManager = new GuiManager();

        getCommand("menuExists").setExecutor(new CheckMenuExistsCommand(guiManager));
        getCommand("menuList").setExecutor(new ListMenusCommand(guiManager));
        getCommand("openMenu").setExecutor(new OpenMenuCommand(guiManager));
        getCommand("openMenu").setTabCompleter(new OpenMenuCommand(guiManager));
        getServer().getPluginManager().registerEvents(new InventoryAction(this), this);

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
