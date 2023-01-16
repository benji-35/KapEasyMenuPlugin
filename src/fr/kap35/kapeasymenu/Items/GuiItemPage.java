package fr.kap35.kapeasymenu.Items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiItemPage implements IGuiItem {

    private int page = 0;
    private ItemStack item = null;

    private ItemGuiAction action = null;
    private int slot = -1;
    private boolean disableEvent = false;

    private JavaPlugin plugin;

    public GuiItemPage(JavaPlugin plugin, ItemStack item, ItemGuiAction action, int slot, int page) {
        this.plugin = plugin;
        this.item = item;
        this.action = action;
        this.slot = slot;
        this.page = page;
    }

    public GuiItemPage(JavaPlugin plugin, ItemStack item, ItemGuiAction action, int slot, int page, boolean disableEvent) {
        this.plugin = plugin;
        this.item = item;
        this.action = action;
        this.slot = slot;
        this.disableEvent = disableEvent;
        this.page = page;
    }

    public GuiItemPage(GuiItem item, int page) {
        this.plugin = item.getPlugin();
        this.item = item.getItem();
        this.action = item.getAction();
        this.slot = item.getSlot();
        this.disableEvent = item.isDisableEvent();
        this.page = page;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void runAction(Player player, InventoryClickEvent event) {
        if (action != null) {
            action.run(player, plugin);
        }
        event.setCancelled(disableEvent);
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public ItemGuiAction getAction() {
        return action;
    }

    @Override
    public boolean isDisableEvent() {
        return disableEvent;
    }

    public int getPage() {
        return page;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuiItemPage)) {
            return false;
        }
        GuiItemPage item = (GuiItemPage) obj;
        return (item.getItem().equals(this.item) && item.getSlot() == this.slot && item.disableEvent == this.disableEvent && item.page == this.page);
    }

    public void setPage(int page) {
        this.page = page;
    }
}
