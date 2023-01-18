package fr.kap35.kapeasymenu.Items;

import fr.kap35.kapeasymenu.listeners.ItemActions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public interface IGuiItem {
    //setters
    public IGuiItem setName(String name);
    public IGuiItem setLore(String[] lore);
    public IGuiItem setAmount(int amount);
    public IGuiItem setAction(ItemActions action, ItemGuiAction effect);
    public void performActions(Player player, InventoryClickEvent event);
    public IGuiItem setDisableEvent(boolean disableEvent);

    //getters
    public Material getMaterial();

    public ItemStack getItem();
    public JavaPlugin getPlugin();
    public ItemGuiAction getAction(ItemActions action);
    public boolean isDisableEvent();
    public String[] getLore();
    public String getName();
}
