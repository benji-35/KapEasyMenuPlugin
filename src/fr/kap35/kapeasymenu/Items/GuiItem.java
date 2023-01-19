package fr.kap35.kapeasymenu.Items;

import fr.kap35.kapeasymenu.listeners.ItemActions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GuiItem implements IGuiItem {

    private ItemStack item = null;

    private Map<ItemActions, ItemGuiAction> actions = new HashMap<ItemActions, ItemGuiAction>();

    private boolean disableEvent = false;

    private JavaPlugin plugin;

    public GuiItem(JavaPlugin plugin, Material material) {
        this.plugin = plugin;
        item = new ItemStack(material, 1);
    }

    public GuiItem(Material material) {
        this.plugin = null;
        item = new ItemStack(material, 1);
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void performActions(Player player, InventoryClickEvent event) {
        switch (event.getClick()) {
            case LEFT:
                ItemGuiAction action = actions.get(ItemActions.LEFT_CLICK);
                if (action != null) {
                    action.run(player, plugin);
                }
                break;
            case RIGHT:
                action = actions.get(ItemActions.RIGHT_CLICK);
                if (action != null) {
                    action.run(player, plugin);
                }
                break;
            case MIDDLE:
                action = actions.get(ItemActions.MIDDLE_CLICK);
                if (action != null) {
                    action.run(player, plugin);
                }
                break;
            default:
                break;
        }
        if (disableEvent)
            event.setCancelled(true);
    }

    @Override
    public GuiItem setDisableEvent(boolean disableEvent) {
        this.disableEvent = disableEvent;
        return this;
    }

    @Override
    public Material getMaterial() {
        return item.getType();
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public ItemGuiAction getAction(ItemActions action) {
        return actions.get(action);
    }

    @Override
    public boolean isDisableEvent() {
        return disableEvent;
    }

    @Override
    public String[] getLore() {
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null) {
            return new String[0];
        }
        return item.getItemMeta().getLore().toArray(new String[0]);
    }

    @Override
    public String getName() {
        if (item == null || item.getItemMeta() == null) {
            return "";
        } else {
            return item.getItemMeta().getDisplayName();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuiItem)) {
            return false;
        }
        GuiItem item = (GuiItem) obj;
        return (item.getItem().equals(this.item) && item.disableEvent == this.disableEvent);
    }

    @Override
    public GuiItem setName(String name) {
        if (item  == null || item.getItemMeta() == null) {
            return this;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    @Override
    public GuiItem setLore(String[] lore) {
        if (item  == null || item.getItemMeta() == null) {
            return this;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return this;
    }

    @Override
    public GuiItem setAmount(int amount) {
        if (item  == null) {
            return this;
        }
        item.setAmount(amount);
        return this;
    }

    @Override
    public GuiItem setAction(ItemActions action, ItemGuiAction effect) {
        if (action != null) {
            if (effect != null) {
                actions.remove(action);
                actions.put(action, effect);
            }
        }
        return this;
    }
}
