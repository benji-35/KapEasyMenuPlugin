package fr.kap35.kapeasymenu.listeners;

import fr.kap35.kapeasymenu.KapEasyMenu;
import fr.kap35.kapeasymenu.Menu.GuiManager;
import fr.kap35.kapeasymenu.Menu.IGuiMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryAction implements Listener {

    KapEasyMenu plugin;
    GuiManager guiManager;

    public InventoryAction(KapEasyMenu plugin, GuiManager guiManager) {
        this.plugin = plugin;
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryAction(InventoryClickEvent event) {
        for (IGuiMenu menu: guiManager.getMenus()) {
            if (menu.getTitle().equals(event.getView().getTitle())) {
                menu.checkAction(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        guiManager.onInventoryClose(event);
    }
}
