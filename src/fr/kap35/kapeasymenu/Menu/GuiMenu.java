package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.GuiItemPage;
import fr.kap35.kapeasymenu.Items.IGuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class GuiMenu implements IGuiMenu {

    private int size = 0;
    private Inventory gui;
    private String permission = "";
    private ArrayList<GuiItem> items = new ArrayList<>();

    private JavaPlugin plugin;

    private String title = "";

    public GuiMenu(JavaPlugin plugin, int size, String title) {
        this.size = size;
        this.plugin = plugin;
        this.title = title;
        this.gui = Bukkit.createInventory(null, size, title);
        initGUI();
    }

    protected void initGUI() {}

    protected void updateGUI() {

    }

    @Override
    public void openGUI(Player player) {
        if (permission.equals("") || player.hasPermission(permission)) {
            if (items.size() == 0) {
                player.sendMessage("Menu " + title + " is empty !");
                return;
            }
            __updateGUI();
            updateGUI();
            player.openInventory(gui);
        } else {
            player.sendMessage("You don't have the permission to open this GUI !");
        }
    }

    @Override
    public void openGUI(Player player, int page) {
        openGUI(player);
    }

    @Override
    public void addItem(GuiItem item) {
        System.out.println("Add item : " + item.getItem().toString() + " to menu " + title);
        items.add(item);
    }

    @Override
    public void addItem(GuiItemPage item) {
        GuiItem nitem = new GuiItem(plugin, item.getItem(), item.getAction(), item.getSlot());
        addItem(nitem);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getSize() {
        return size;
    }

    public ArrayList<IGuiItem> getItems() {
        return new ArrayList<>(this.items);
    }

    private void __updateGUI() {
        gui.clear();
        for (GuiItem item : items) {
            if (item.getSlot() != -1) {
                gui.setItem(item.getSlot(), item.getItem());
            }
        }
    }

    protected JavaPlugin getPlugin() {
        return plugin;
    }

    public void checkAction(InventoryClickEvent event) {
        for (GuiItem item : items) {
            if (item.getSlot() == event.getSlot() && item.getItem().equals(event.getCurrentItem())) {
                item.runAction((Player) event.getWhoClicked(), event);
            }
        }
    }
}
