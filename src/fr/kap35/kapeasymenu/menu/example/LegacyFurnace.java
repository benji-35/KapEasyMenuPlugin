package fr.kap35.kapeasymenu.menu.example;

import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.menu.legacy.FurnaceMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LegacyFurnace extends FurnaceMenu {

    public LegacyFurnace(JavaPlugin plugin) {
        super(plugin, "Furnace test");

        GuiItem item1 = new GuiItem(plugin, Material.ARROW);
        GuiItem item2 = new GuiItem(plugin, Material.BEDROCK);
        GuiItem item3 = new GuiItem(plugin, Material.REDSTONE_BLOCK);

        addItem(item1, FurnacePlace.RESULT);
        addItem(item2, FurnacePlace.SMELTING);
        addItem(item3, FurnacePlace.FUEL);
    }

    @Override
    protected void onOpenMenu(Player player) {

    }

    @Override
    protected void onCloseMenu(Player player) {

    }
}
