package fr.kap35.kapeasymenu.listeners;

import fr.kap35.kapeasymenu.KapEasyMenu;
import fr.kap35.kapeasymenu.Menu.GuiManager;
import fr.kap35.kapeasymenu.Menu.IGuiMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryAction implements Listener {

    KapEasyMenu plugin;

    public InventoryAction(KapEasyMenu plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryAction(InventoryClickEvent event) {
        GuiManager manager = plugin.getGuiManager();
        for (IGuiMenu menu: manager.getMenus()) {
            if (menu.getTitle().equals(event.getView().getTitle())) {
                event.setCancelled(true);
                menu.checkAction(event);
            }
        }
    }

    @EventHandler
    public void onInevntoryClose(InventoryCloseEvent event) {

    }
}
