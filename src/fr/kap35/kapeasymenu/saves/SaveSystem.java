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
        initFile();
    }

    private void initFile() {
        File _config = new File(plugin.getDataFolder(), CONFIG_FILE_NAME);

        if (!_config.exists()) {
            try {
                Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4The file " + CONFIG_FILE_NAME + " doesn't exist, creating it...");
                if (_config.getParentFile().mkdirs() && _config.createNewFile()) {
                    Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §2The file " + CONFIG_FILE_NAME + " has been created !");
                    plugin.saveResource("kapAdmin.yml", false);
                } else if (_config.createNewFile()) {
                    Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §2The file " + CONFIG_FILE_NAME + " has been created !");
                } else {
                    Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4The file " + CONFIG_FILE_NAME + " can't be created !");
                    return;
                }
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Error while creating config file !");
                return;
            }
        }

        config = new YamlConfiguration();
        try {
            config.load(_config);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Error while loading config file !");
            e.printStackTrace();
        }
    }

    public boolean getStateMenu(String menuName) {
        if (config == null) {
            initFile();
        }
        if (config == null)
            return true;
        if (config.contains("menus." + menuName + ".state")) {
            return config.getBoolean("menus." + menuName + ".state");
        } else {
            return true;
        }
    }

    public void setStateMenu(String menuName, boolean state) {
        if (config == null) {
            initFile();
        }
        if (config == null)
            return;
        config.set("menus." + menuName + ".state", state);
        save();
    }

    private void save() {
        try {
            config.save(new File(plugin.getDataFolder(), CONFIG_FILE_NAME));
            config.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
