package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.GuiItemPage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface IGuiMenu {
    public String getTitle();
    public int getSize();
    public void addItem(GuiItem item);
    public void addItem(GuiItemPage item);
    public void openGUI(Player player);
    public void openGUI(Player player, int page);
    public void checkAction(InventoryClickEvent event);
}
