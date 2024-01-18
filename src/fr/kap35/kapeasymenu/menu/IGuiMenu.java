package fr.kap35.kapeasymenu.menu;

import fr.kap35.kapeasymenu.Items.IGuiItem;
import fr.kap35.kapeasymenu.menu.exception.CannotPlaceItemException;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IGuiMenu {

    String getTitle();
    int getSize();
    Set<IGuiItem> getItems();

    void setEnable(boolean enable);
    boolean isEnable();
    void openGUI(Player player);
    void checkAction(InventoryClickEvent event);
    void closingMenu(Player player);

    Player[] getReaders();
    int getReaderAmount();
}
