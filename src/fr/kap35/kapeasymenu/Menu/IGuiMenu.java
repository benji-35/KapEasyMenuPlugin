package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.IGuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Map;

public interface IGuiMenu {


    //setters
    public void addItem(IGuiItem item, int slot, int page);
    public void addItem(IGuiItem item, int slot);

    //getters
    Collection<IGuiItem> getItems();
    public String getTitle();
    public int getSize();
    public boolean isStatic();
    public JavaPlugin getPlugin();
    int getReaderAmount();

    //actions
    public void openGUI(Player player);
    public void openGUI(Player player, int page);
    public void checkAction(InventoryClickEvent event);

    //events
    public void onOpenMenu(Player player);
    public void onCloseMenu(Player player);
    public void onInit();
}
