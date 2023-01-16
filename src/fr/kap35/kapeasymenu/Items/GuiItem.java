package fr.kap35.kapeasymenu.Items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiItem {

    private ItemStack item = null;

    private ItemGuiAction action = null;
    private int slot = -1;
    private boolean disableEvent = false;

    private JavaPlugin plugin;

    public GuiItem(JavaPlugin plugin, ItemStack item, ItemGuiAction action, int slot) {
        this.plugin = plugin;
        this.item = item;
        this.action = action;
        this.slot = slot;
    }

    public GuiItem(JavaPlugin plugin, ItemStack item, ItemGuiAction action, int slot, boolean disableEvent) {
        this.plugin = plugin;
        this.item = item;
        this.action = action;
        this.slot = slot;
        this.disableEvent = disableEvent;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void runAction(Player player, InventoryClickEvent event) {
        if (action != null) {
            action.run(player, plugin);
        }
        event.setCancelled(disableEvent);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public ItemGuiAction getAction() {
        return action;
    }

    public boolean isDisableEvent() {
        return disableEvent;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuiItem)) {
            return false;
        }
        GuiItem item = (GuiItem) obj;
        return (item.getItem().equals(this.item) && item.getSlot() == this.slot && item.disableEvent == this.disableEvent);
    }

}
