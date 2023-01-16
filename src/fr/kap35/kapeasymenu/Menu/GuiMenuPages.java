package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Items.BasicItemsGui;
import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.GuiItemPage;
import fr.kap35.kapeasymenu.Items.IGuiItem;
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
    private int maxPage = 0;

    public GuiMenuPages(JavaPlugin plugin, int size, String title) {
        this.plugin = plugin;
        this.title = title;
        this.size = size + 9;
        this.gui = Bukkit.createInventory(null, this.size, title);
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
        //add the item after last slot of all pages

    }

    public void addItem(GuiItem item, int page) {
        GuiItemPage itemPage = new GuiItemPage(item, page);
        addItem(itemPage);
    }

    @Override
    public void addItem(GuiItemPage item) {
        items.add(item);
        if (item.getPage() > maxPage) {
            maxPage = item.getPage();
        }
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
            if (page > maxPage) {
                page = maxPage;
            }
            if (items.size() == 0) {
                player.sendMessage("Menu " + title + " is empty !");
                return;
            }
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
        int slot = event.getSlot();

        if (slot > size * 9) {
            event.setCancelled(true);
            if (slot == ((size * 9) + 4)) {
                page--;
                openGUI((Player) event.getWhoClicked(), page);
                return;
            } else if (slot == ((size * 9) + 6)) {
                page++;
                openGUI((Player) event.getWhoClicked(), page);
                return;
            }
        }

        for (GuiItemPage item : items) {
            if (item.getSlot() == event.getSlot() && item.getItem().equals(event.getCurrentItem())) {
                item.runAction((Player) event.getWhoClicked(), event);
            }
        }
    }

    @Override
    public ArrayList<IGuiItem> getItems() {
        return new ArrayList<>(this.items);
    }

    private void __updateGUI() {
        gui.clear();
        for (GuiItemPage item : items) {
            if (item.getSlot() != -1 && item.getPage() == page) {
                gui.setItem(item.getSlot(), item.getItem());
            }
        }
        int slotStart = size - 9;
        for (int i = 0; i < 9; i++) {
            gui.setItem(slotStart + i, BasicItemsGui.emptySlotItem());
        }
        gui.setItem(slotStart + 3, BasicItemsGui.previousItem());
        gui.setItem(slotStart + 5, BasicItemsGui.nextItem());
    }

    public void appendItem(GuiItemPage item) {
        int lastPosition = 0;
        if (items.size() == 0) {
            item.setSlot(lastPosition + 1);
            item.setPage(0);
            return;
        }
        for (GuiItemPage itemPage : items) {
            int calculatedSlot = itemPage.getSlot() + (size * 9 * itemPage.getPage());
            if (calculatedSlot > lastPosition) {
                lastPosition = calculatedSlot;
            }
        }
        int page = 0;
        while (lastPosition >= size - 9) {
            lastPosition -= size - 9;
            page++;
        }
        item.setSlot(lastPosition + 1);
        item.setPage(page);
        if (page > maxPage) {
            maxPage = page;
        }
    }

    public void setItemsList(List<GuiItem> items) {
        int indexCalc = 0;
        int page = 0;

        for (GuiItem item : items) {
            indexCalc++;
            if (indexCalc > size - 9) {
                indexCalc = 0;
                page++;
            }
            GuiItemPage itemPage = new GuiItemPage(item, page);
            itemPage.setSlot(indexCalc);
            addItem(itemPage);
        }
        if (page > maxPage) {
            maxPage = page;
        }
    }

    public void clearPage() {
        items.clear();
        maxPage = 0;
        page = 0;
    }
}
