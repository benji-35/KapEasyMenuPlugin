package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Items.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class GuiMenu {

    private Inventory gui;
    private String permission = "";
    private boolean page = false;
    private ArrayList<GuiItem> items = new ArrayList<>();

    private JavaPlugin plugin;

    private String title = "";

    public GuiMenu(JavaPlugin plugin, int size, String title) {

        this.plugin = plugin;
        this.title = title;
        this.gui = Bukkit.createInventory(null, size, title);
        initGUI();
    }

    protected void initGUI() {}

    protected void updateGUI() {

    }

    protected void setPage(boolean page) {
        this.page = page;
    }

    protected boolean isPage() {
        return page;
    }

    public void openGUI(Player player) {
        if (permission.equals("") || player.hasPermission(permission)) {
            __updateGUI();
            updateGUI();
            player.openInventory(gui);
        } else {
            player.sendMessage("You don't have the permission to open this GUI !");
        }
    }

    public void addItem(GuiItem item) {
        System.out.println("Add item : " + item.getItem().toString() + " to menu " + title);
        items.add(item);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<GuiItem> getItems() {
        return items;
    }

    private void __updateGUI() {
        gui.clear();
        for (GuiItem item : items) {
            System.out.println("Item : " + item.getItem().getType().toString() + " - slot: " + item.getSlot());
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
