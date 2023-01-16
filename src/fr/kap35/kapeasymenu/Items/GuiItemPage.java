package fr.kap35.kapeasymenu.Items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiItemPage extends GuiItem {

    private int page;

    public GuiItemPage(GuiItem item, int page) {
        super(item.getPlugin(), item.getItem(), item.getAction(), item.getSlot(), item.isDisableEvent());
        this.page = page;
    }

    public GuiItemPage(JavaPlugin plugin, ItemStack item, ItemGuiAction action, int slot) {
        super(plugin, item, action, slot);
    }

    public GuiItemPage(JavaPlugin plugin, ItemStack item, ItemGuiAction action, int slot, boolean disableEvent) {
        super(plugin, item, action, slot, disableEvent);
    }

    public void GuiItemPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
