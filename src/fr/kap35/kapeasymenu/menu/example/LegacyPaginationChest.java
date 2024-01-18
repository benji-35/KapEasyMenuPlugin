package fr.kap35.kapeasymenu.menu.example;

import fr.kap35.kapeasymenu.Items.BasicItemsGui;
import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.menu.exception.CannotPlaceItemException;
import fr.kap35.kapeasymenu.menu.exception.MenuSizeException;
import fr.kap35.kapeasymenu.menu.legacy.ChestPaginationMenu;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LegacyPaginationChest extends ChestPaginationMenu {

    public LegacyPaginationChest(JavaPlugin plugin) throws MenuSizeException, CannotPlaceItemException {
        super(plugin, "Pagination Menu Example", 27);
        GuiItem item1 = new GuiItem(plugin, BasicItemsGui.backItem())
                .setName("Example 1");
        GuiItem item2 = new GuiItem(plugin, BasicItemsGui.backItem())
                .setName("Example 2");

        addItem(item1, 9, 0);
        addItem(item2, 9, 1);
    }

    @Override
    protected void onSwitchPage(Player player, int page) {
        player.sendMessage("You switched page from menu " + getTitle());
    }

    @Override
    protected void onCloseMenu(Player player) {
        player.sendMessage("You closed menu " + getTitle());
    }

    @Override
    protected void onOpenMenu(Player player) {
        player.sendMessage("You opened menu " + getTitle());
    }
}
