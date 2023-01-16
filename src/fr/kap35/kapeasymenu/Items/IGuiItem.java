package fr.kap35.kapeasymenu.Items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public interface IGuiItem {

    public ItemStack getItem();
    public int getSlot();
    public JavaPlugin getPlugin();
    public ItemGuiAction getAction();
    public boolean isDisableEvent();

    public void setSlot(int slot);

}
