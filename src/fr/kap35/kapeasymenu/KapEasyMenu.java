package fr.kap35.kapeasymenu;

import fr.kap35.kapeasymenu.Interfaces.IGuiManager;
import fr.kap35.kapeasymenu.menu.GuiManager;
import fr.kap35.kapeasymenu.clock.ClockChecking;
import fr.kap35.kapeasymenu.commands.CheckMenuExistsCommand;
import fr.kap35.kapeasymenu.commands.ListMenusCommand;
import fr.kap35.kapeasymenu.commands.OpenMenuCommand;
import fr.kap35.kapeasymenu.commands.SetActiveMenuCommand;
import fr.kap35.kapeasymenu.listeners.InventoryAction;
import fr.kap35.kapeasymenu.menu.example.LegacyChest;
import fr.kap35.kapeasymenu.menu.example.LegacyFurnace;
import fr.kap35.kapeasymenu.menu.example.LegacyPaginationChest;
import fr.kap35.kapeasymenu.menu.exception.CannotPlaceItemException;
import fr.kap35.kapeasymenu.menu.exception.MenuSizeException;
import fr.kap35.kapeasymenu.versioning.IVersioningService;
import fr.kap35.kapeasymenu.versioning.VersioningSystem;
import fr.kap35.kapeasymenu.versioning.dto.VersionDifferenceDto;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class KapEasyMenu extends JavaPlugin {

    GuiManager guiManager;
    ClockChecking clockChecking;
    IVersioningService versioningService;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        versioningService = new VersioningSystem();
        guiManager = new GuiManager(this);

        try {
            guiManager.registerMenus(new LegacyFurnace(this), "LegacyFurnaceMenu");
            guiManager.registerMenus(new LegacyChest(this), "LegacyChestMenu");
            guiManager.registerMenus(new LegacyPaginationChest(this), "LegacyPaginationChestMenu");
        } catch (MenuSizeException | CannotPlaceItemException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "/!\\" +
                    ChatColor.DARK_BLUE + "[KapEasyMenu] " + ChatColor.RED + "/!\\"
                    + ChatColor.RESET + "Failed to load legacies menu: ["
                    + e.getClass().getName() + "] "
                    + e.getMessage());
        }

        Objects.requireNonNull(getCommand("menuExists")).setExecutor(new CheckMenuExistsCommand(guiManager));
        Objects.requireNonNull(getCommand("menuList")).setExecutor(new ListMenusCommand(guiManager));
        Objects.requireNonNull(getCommand("openMenu")).setExecutor(new OpenMenuCommand(guiManager));
        Objects.requireNonNull(getCommand("openMenu")).setTabCompleter(new OpenMenuCommand(guiManager));
        Objects.requireNonNull(getCommand("setmenuactive")).setExecutor(new SetActiveMenuCommand(guiManager));
        Objects.requireNonNull(getCommand("setmenuactive")).setTabCompleter(new SetActiveMenuCommand(guiManager));
        getServer().getPluginManager().registerEvents(new InventoryAction(this, guiManager), this);

        clockChecking = new ClockChecking(this, guiManager);
        updateChecking();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[KapEasyMenu] " + ChatColor.GREEN + "Plugin enabled !");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[KapEasyMenu] " + ChatColor.RED + "Plugin disable !");
    }

    private void updateChecking() {
        if (versioningService.isLatestVersion()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[KapEasyMenu] " + ChatColor.GREEN  + "Plugin is up to date !");
            return;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "/!\\" + ChatColor.DARK_BLUE + "[KapEasyMenu]" + ChatColor.YELLOW + "/!\\ " +
                ChatColor.RESET + "plugin is not up to date, current: " + versioningService.getCurrentVersion().version + ", latest: " + versioningService.getLatestVersion().version);
        List<VersionDifferenceDto> dangerous = versioningService.getDangerous();
        List<VersionDifferenceDto> majors = versioningService.getMajors();
        List<VersionDifferenceDto> minors = versioningService.getMinors();
        if (!dangerous.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "/!\\" + ChatColor.DARK_BLUE + "[KapEasyMenu]" + ChatColor.RED + "/!\\ " +
                    "List of dangerous issues to fix with update:");
            for(int i = 0; i < dangerous.size(); i++) {
                VersionDifferenceDto version = dangerous.get(i);
                Bukkit.getConsoleSender().sendMessage("    - " + version.description + " [" + version.getVersionToDebug() + "]");
            }
        }
        if (!majors.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "/!\\" + ChatColor.DARK_BLUE + "[KapEasyMenu]" + ChatColor.YELLOW + "/!\\ " +
                    "List of majors update to catch:");
            for(int i = 0; i < majors.size(); i++) {
                VersionDifferenceDto version = majors.get(i);
                Bukkit.getConsoleSender().sendMessage("    - " + version.description + " [" + version.getVersionToDebug() + "]");
            }
        }
        if (!minors.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "/!\\" + ChatColor.DARK_BLUE + "[KapEasyMenu]" + ChatColor.BLUE + "/!\\ " +
                    "List of minors update to catch:");
            for(int i = 0; i < minors.size(); i++) {
                VersionDifferenceDto version = minors.get(i);
                Bukkit.getConsoleSender().sendMessage("    - " + version.description + " [" + version.getVersionToDebug() + "]");
            }
        }
    }

    public IGuiManager getGuiManager() {
        return guiManager;
    }
    public IVersioningService getVersion() {
        return versioningService;
    }
}
