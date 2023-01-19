package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.debug.Debug;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiManager {

    Debug debug;
    Map<String, IGuiMenu> menus = new HashMap<>();

    public GuiManager() {
        debug = new Debug();
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
            menus.put(name, menu);
        }
    }

    public void openMenu(Player player, String name) {
        if (menus.containsKey(name) && menus.get(name) != null) {
            menus.get(name).openGUI(player);
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
            }
        }
    }
}
