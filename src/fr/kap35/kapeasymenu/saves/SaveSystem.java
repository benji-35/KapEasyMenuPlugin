package fr.kap35.kapeasymenu.saves;

import fr.kap35.kapeasymenu.KapEasyMenu;
import fr.kap35.kapeasymenu.Menu.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SaveSystem {

    FileConfiguration config;
    KapEasyMenu plugin;

    private final String CONFIG_FILE_NAME = "kapEasyMenu.yml";

    public SaveSystem(KapEasyMenu plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
    }

    public boolean getStateMenu(String menuName) {
        if (config == null)
            return true;
        if (config.contains("menus." + menuName + ".state")) {
            return config.getBoolean("menus." + menuName + ".state");
        } else {
            return true;
        }
    }

    public void setStateMenu(String menuName, boolean state) {
        if (config == null)
            return;
        config.set("menus." + menuName + ".state", state);
        save();
    }

    private void save() {
        plugin.saveConfig();
    }
}
