package fr.kap35.kapeasymenu;

import fr.kap35.kapeasymenu.Interfaces.IGuiManager;
import fr.kap35.kapeasymenu.Menu.GuiManager;
import fr.kap35.kapeasymenu.clock.ClockChecking;
import fr.kap35.kapeasymenu.commands.CheckMenuExistsCommand;
import fr.kap35.kapeasymenu.commands.ListMenusCommand;
import fr.kap35.kapeasymenu.commands.OpenMenuCommand;
import fr.kap35.kapeasymenu.commands.SetActiveMenuCommand;
import fr.kap35.kapeasymenu.listeners.InventoryAction;
import fr.kap35.kapeasymenu.versioning.IVersioningService;
import fr.kap35.kapeasymenu.versioning.VersioningSystem;
import fr.kap35.kapeasymenu.versioning.dto.VersionDifferenceDto;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URL;
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
        if (!versioningService.getDangerous().isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "/!\\" + ChatColor.DARK_BLUE + "[KapEasyMenu]" + ChatColor.RED + "/!\\ " +
                    "List of dangerous issues to fix with update:");
            for(int i = 0; i < versioningService.getDangerous().size(); i++) {
                VersionDifferenceDto version = versioningService.getDangerous().get(i);
                Bukkit.getConsoleSender().sendMessage("    - " + version.description);
            }
        }
        if (!versioningService.getMajors().isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "/!\\" + ChatColor.DARK_BLUE + "[KapEasyMenu]" + ChatColor.YELLOW + "/!\\ " +
                    "List of majors update to catch:");
            for(int i = 0; i < versioningService.getMajors().size(); i++) {
                VersionDifferenceDto version = versioningService.getMajors().get(i);
                Bukkit.getConsoleSender().sendMessage("    - " + version.description);
            }
        }
        if (!versioningService.getMinors().isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "/!\\" + ChatColor.DARK_BLUE + "[KapEasyMenu]" + ChatColor.BLUE + "/!\\ " +
                    "List of minors update to catch:");
            for(int i = 0; i < versioningService.getMinors().size(); i++) {
                VersionDifferenceDto version = versioningService.getMinors().get(i);
                Bukkit.getConsoleSender().sendMessage("    - " + version.description);
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
