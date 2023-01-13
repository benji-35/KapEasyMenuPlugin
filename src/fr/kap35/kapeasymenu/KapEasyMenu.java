package fr.kap35.kapeasymenu;

import fr.kap35.kapeasymenu.Menu.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class KapEasyMenu extends JavaPlugin {

    GuiManager guiManager;

    @Override
    public void onEnable() {
        guiManager = new GuiManager();
        Bukkit.getConsoleSender().sendMessage("KapEasyMenu is enable !");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("KapEasyMenu is disable !");
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
