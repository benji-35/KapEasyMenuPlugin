package fr.kap35.kapeasymenu.Menu;

import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.IGuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GuiMenu implements IGuiMenu {

    Map<Integer, GuiItem> items = new HashMap<>();
    Map<Integer, GuiItem> itemsTmp = new HashMap<>();
    List<Player> readers = new ArrayList<>();
    Inventory gui = null;
    String title;
    int size;
    boolean isStatic;
    protected JavaPlugin plugin;
    boolean isTmpAdding = false;

    public GuiMenu(JavaPlugin plugin, String title, int size, boolean isStatic) {
        this.title = title;
        this.size = size;
        this.isStatic = isStatic;
        this.plugin = plugin;
        gui = Bukkit.createInventory(null, size, title);
        onInit();
    }

    public GuiMenu(JavaPlugin plugin, String title, int size) {
        this(plugin, title, size, true);
    }

    @Override
    public void addItem(IGuiItem item, int slot, int page) {
        addItem(item, slot);
    }

    @Override
    public void addItem(IGuiItem item, int slot) {
        if (!isTmpAdding) {
            if (items.containsKey(slot)) {
                Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Slot already used, replacing item");
                items.remove(slot);
            }
            items.put(slot, (GuiItem) item);
        } else {
            if (itemsTmp.containsKey(slot)) {
                Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Slot already used, replacing item");
                itemsTmp.remove(slot);
            }
            itemsTmp.put(slot, (GuiItem) item);
        }
    }

    @Override
    public Collection<IGuiItem> getItems() {
        return new ArrayList<>(items.values());
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
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public int getReaderAmount() {
        return readers.size();
    }

    @Override
    public void onCloseMenu(Player player) {
        readers.remove(player);
    }

    @Override
    public void openGUI(Player player) {
        gui.clear();
        readers.remove(player);
        readers.add(player);
        for (int i = 0; i < size; i++) {
            if (items.containsKey(i)) {
                gui.setItem(i, items.get(i).getItem());
            }
        }
        if (isStatic) {
            player.openInventory(gui);
            return;
        }
        Inventory guiTmp = Bukkit.createInventory(null, size, title);
        for (int i = 0; i < size; i++) {
            if (items.containsKey(i)) {
                guiTmp.setItem(i, items.get(i).getItem());
            }
        }
        isTmpAdding = true;
        onOpenMenu(player);
        isTmpAdding = false;
        for (int i = 0; i < size; i++) {
            if (itemsTmp.containsKey(i)) {
                guiTmp.setItem(i, itemsTmp.get(i).getItem());
            }
        }
        itemsTmp.clear();
        player.openInventory(guiTmp);
    }

    @Override
    public void openGUI(Player player, int page) {
        openGUI(player);
    }

    @Override
    public void checkAction(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getView().getTitle().equals(title)) {
            if (items.containsKey(event.getSlot())) {
                items.get(event.getSlot()).performActions((Player) event.getWhoClicked(), event);
            }
        }
    }

    @Override
    public void onOpenMenu(Player player) {}

    @Override
    public void onInit() {}
}
