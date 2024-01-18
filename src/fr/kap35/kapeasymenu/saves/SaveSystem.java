package fr.kap35.kapeasymenu.saves;

import fr.kap35.kapeasymenu.KapEasyMenu;
import org.bukkit.configuration.file.FileConfiguration;

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
