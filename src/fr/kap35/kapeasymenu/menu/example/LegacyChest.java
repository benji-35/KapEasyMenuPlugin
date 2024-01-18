package fr.kap35.kapeasymenu.menu.example;

import fr.kap35.kapeasymenu.Items.BasicItemsGui;
import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.debug.Debug;
import fr.kap35.kapeasymenu.menu.IGuiMenu;
import fr.kap35.kapeasymenu.menu.exception.MenuSizeException;
import fr.kap35.kapeasymenu.menu.legacy.ChestMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LegacyChest extends ChestMenu {

    public LegacyChest(JavaPlugin plugin) throws MenuSizeException {
        super(plugin, "Menu Example", 27);
        GuiItem item1 = new GuiItem(plugin, BasicItemsGui.backItem())
                .setName("Example 1");
        GuiItem item2 = new GuiItem(plugin, BasicItemsGui.backItem())
                .setName("Example 2");
        addItem(item1, 3);
        addItem(item2, 17);
    }

    @Override
    protected void onCloseMenu(Player player) {
        player.sendMessage("You are closing menu " + getTitle());
    }

    @Override
    protected void onOpenMenu(Player player) {
        player.sendMessage("You are opening menu " + getTitle());
    }
}
