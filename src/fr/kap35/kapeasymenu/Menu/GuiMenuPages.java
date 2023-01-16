package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.GuiItemPage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GuiMenuPages implements IGuiMenu {

    List<GuiItemPage> items = new ArrayList<>();
    private Inventory gui;
    private String permission = "";

    private JavaPlugin plugin;

    private String title = "";
    private int size = 0;
    private int page = 0;

    public GuiMenuPages(JavaPlugin plugin, int size, String title) {
        this.plugin = plugin;
        this.title = title;
        this.size = size;
        this.gui = Bukkit.createInventory(null, size, title);
        initGUI();
    }

    protected void initGUI() {}

    protected void updateGUI() {

    }

    protected JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void addItem(GuiItem item) {
        GuiItemPage itemPage = new GuiItemPage(item, 0);
        items.add(itemPage);
    }

    public void addItem(GuiItem item, int page) {
        GuiItemPage itemPage = new GuiItemPage(item, page);
        items.add(itemPage);
    }

    @Override
    public void addItem(GuiItemPage item) {
        items.add(item);
    }

    @Override
    public void openGUI(Player player) {
        if (permission.equals("") || player.hasPermission(permission)) {
            __updateGUI();
            updateGUI();
            player.openInventory(gui);
        } else {
            player.sendMessage("You don't have the permission to open this GUI !");
        }
    }

    @Override
    public void openGUI(Player player, int page) {
        if (permission.equals("") || player.hasPermission(permission)) {
            this.page = page;
            __updateGUI();
            updateGUI();
            player.openInventory(gui);
        } else {
            player.sendMessage("You don't have the permission to open this GUI !");
        }
    }

    @Override
    public void checkAction(InventoryClickEvent event) {
        for (GuiItem item : items) {
            if (item.getSlot() == event.getSlot() && item.getItem().equals(event.getCurrentItem())) {
                item.runAction((Player) event.getWhoClicked(), event);
            }
        }
    }

    private void __updateGUI() {
        gui.clear();
        for (GuiItemPage item : items) {
            if (item.getSlot() != -1 && item.getPage() == page) {
                gui.setItem(item.getSlot(), item.getItem());
            }
        }
    }
}
