package fr.kap35.kapeasymenu.listeners;

import fr.kap35.kapeasymenu.KapEasyMenu;
import fr.kap35.kapeasymenu.menu.GuiManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryAction implements Listener {

    KapEasyMenu plugin;
    GuiManager guiManager;

    public InventoryAction(KapEasyMenu plugin, GuiManager guiManager) {
        this.plugin = plugin;
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryAction(InventoryClickEvent event) {
        guiManager.checkMenusActions(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        guiManager.onInventoryClose(event);
    }
}
