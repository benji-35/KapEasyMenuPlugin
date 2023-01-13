package fr.kap35.kapeasymenu.Menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiManager {

    Map<String, GuiMenu> menus = new HashMap<>();

    public void registerMenus(GuiMenu menu, String name) {
        if (menus.containsKey(name)) {
            System.out.println("Menu " + name + " already exist !");
        } else {
            menus.put(name, menu);
        }
    }

    public void openMenu(Player player, String name) {
        if (menus.containsKey(name)) {
            menus.get(name).openGUI(player);
        } else {
            System.out.println("Menu " + name + " not found !");
        }
    }

    public GuiMenu getMenu(String name) {
        if (menus.containsKey(name)) {
            return menus.get(name);
        } else {
            System.out.println("Menu " + name + " not found !");
            return null;
        }
    }

    public ArrayList<GuiMenu> getMenus() {
        return new ArrayList<>(menus.values());
    }

    public void checkMenusActions(InventoryClickEvent event) {
        for (GuiMenu menu : getMenus()) {
            if (menu.getTitle().equals(event.getView().getTitle())) {
                menu.checkAction(event);
            }
        }
    }
}