package fr.kap35.kapeasymenu.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BasicItemsGui {

    public static ItemStack nextItem() {
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("Next");
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack previousItem() {
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("Previous");
        }

        item.setItemMeta(meta);
        return item;
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

    public static ItemStack emptySlotItem() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("");
        }

        item.setItemMeta(meta);
        return item;
    }
}
