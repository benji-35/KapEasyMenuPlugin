package fr.kap35.kapeasymenu.Items;

import fr.kap35.kapeasymenu.listeners.ItemActions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicItemsGui {

    public static GuiItem nextItem(JavaPlugin plugin) {
        return new GuiItem(plugin, Material.ARROW).setAmount(1).setName("Next").setLore(new String[0]).setDisableEvent(true);
    }

    public static GuiItem previousItem(JavaPlugin plugin) {
        return new GuiItem(plugin, Material.ARROW).setAmount(1).setName("Previous").setLore(new String[0]).setDisableEvent(true);
    }

    public static ItemStack closeItem() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("Close");
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack backItem() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("Back");
        }

        item.setItemMeta(meta);
        return item;
    }

    public static GuiItem emptySlotItem() {
        return new GuiItem(null, Material.GRAY_STAINED_GLASS_PANE).setAmount(1).setName("").setLore(new String[0]).setDisableEvent(true);
    }

    public static GuiItem pageNumberItem(int page, int pageAmount) {
        return new GuiItem(null, Material.PAPER).
                setAmount(1).
                setName("Page [" + page + "/" + pageAmount + "]").
                setLore(new String[0]).setDisableEvent(true);
    }
}
