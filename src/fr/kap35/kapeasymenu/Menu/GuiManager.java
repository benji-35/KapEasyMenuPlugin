package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Interfaces.IGuiManager;
import fr.kap35.kapeasymenu.KapEasyMenu;
import fr.kap35.kapeasymenu.debug.Debug;
import fr.kap35.kapeasymenu.saves.SaveSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager implements IGuiManager {

    Debug debug;
    Map<String, IGuiMenu> menus = new HashMap<>();
    KapEasyMenu plugin;
    SaveSystem saveSystem;
    private int maxWaitingTime = 2;

    public GuiManager(KapEasyMenu plugin) {
        debug = new Debug();
        this.plugin = plugin;
        saveSystem = new SaveSystem(plugin);
    }

    public void registerMenus(IGuiMenu menu, String name) {
        String invName = menu.getTitle();
        if (menus.containsKey(name)) {
            debug.printErrorAddingMenu("The menu with key " + name + " already exist !");
        } else {
            for (IGuiMenu menu1 : menus.values()) {
                if (menu1.getTitle().equals(invName)) {
                    debug.printErrorAddingMenu("The menu with title " + invName + " already exist !");
                    return;
                }
            }
            menu.setEnable(saveSystem.getStateMenu(name));
            menus.put(name, menu);
        }
    }

    public void openMenu(Player player, String name) {
        player.closeInventory();
        if (menus.containsKey(name) && menus.get(name) != null) {
            IGuiMenu menu = menus.get(name);
            if (menu.isEnable()) {
                menu.openGUI(player);
            } else {
                debug.printErrorOpeningMenu("The menu with key " + name + " is disable !");
            }
        } else {
            debug.printErrorMenuNotFound(name);
        }
    }

    public IGuiMenu getMenu(String name) {
        if (menus.containsKey(name)) {
            return menus.get(name);
        } else {
            debug.printErrorMenuNotFound(name);
            return null;
        }
    }

    public ArrayList<IGuiMenu> getMenus() {
        return new ArrayList<>(menus.values());
    }

    public ArrayList<String> getAllMenuKeys() { return new ArrayList<>(menus.keySet()); }

    public void checkMenusActions(InventoryClickEvent event) {
        for (IGuiMenu menu : getMenus()) {
            if (menu.getTitle().equals(event.getView().getTitle())) {
                menu.checkAction(event);
            }
        }
    }

    public boolean menuExists(String name) {
        return menus.containsKey(name);
    }

    public void onInventoryClose(InventoryCloseEvent event) {
        for (IGuiMenu menu : getMenus()) {
            if (menu.getTitle().equals(event.getView().getTitle())) {
                menu.onCloseMenu((Player) event.getPlayer());
                if (menu instanceof GuiMenuPages) {
                    GuiMenuPages _menu = (GuiMenuPages) menu;
                    Player player = (Player) event.getPlayer();
                }
            }
        }
    }

    public void setMenuEnable(String name, boolean enable) {
        if (menus.containsKey(name)) {
            menus.get(name).setEnable(enable);
            saveSystem.setStateMenu(name, enable);
        }
    }

    public void checkReaders() {
        for (IGuiMenu menu : getMenus()) {
            Player[] readers = menu.getReaders();
            for (Player player : readers) {
                if (player == null) {
                    menu.__removeReader(player);
                } else {
                    if (player.getOpenInventory() != null) {
                        if (!player.getOpenInventory().getTitle().equals(menu.getTitle())) {
                            menu.__removeReader(player);
                        }
                    } else {
                        menu.__removeReader(player);
                    }
                }
            }
        }
    }
}
