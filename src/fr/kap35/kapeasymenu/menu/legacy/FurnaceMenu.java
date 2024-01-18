package fr.kap35.kapeasymenu.menu.legacy;

import fr.kap35.kapeasymenu.Items.IGuiItem;
import fr.kap35.kapeasymenu.menu.IGuiMenu;
import fr.kap35.kapeasymenu.menu.exception.CannotPlaceItemException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public abstract class FurnaceMenu implements IGuiMenu {

    private String title;
    private Inventory menu;
    private JavaPlugin plugin;
    private Map<FurnacePlace, IGuiItem> items;
    private Map<Player, Map<FurnacePlace, IGuiItem>> dynamicsItem;
    private List<Player> readers;
    private boolean enabled;

    public FurnaceMenu(JavaPlugin plugin, String title) {
        this.title = title;
        this.plugin = plugin;
        this.menu = Bukkit.createInventory(null, InventoryType.FURNACE, title);
        this.enabled = true;
        this.items = new HashMap<>();
        this.dynamicsItem = new HashMap<>();
        this.readers = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public int getSize() {
        return 2;
    }
    @Override
    public Set<IGuiItem> getItems() {
        return new HashSet<>(items.values());
    }
    @Override
    public void setEnable(boolean enable) {
        enabled = enable;
    }
    @Override
    public boolean isEnable() {
        return enabled;
    }
    @Override
    public void openGUI(Player player) {
        player.openInventory(menu);
        openMenu(player);
    }
    @Override
    public void checkAction(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slotClicked = event.getSlot();
        if (!readers.contains(player))
            return;
        if (dynamicsItem.containsKey(player)) {
            for (Map.Entry<FurnacePlace, IGuiItem> entry : dynamicsItem.get(player).entrySet()) {
                if (slotClicked == entry.getKey().index) {
                    entry.getValue().performActions(player, event);
                    return;
                }
            }
        }
        for (Map.Entry<FurnacePlace, IGuiItem> entry : items.entrySet()) {
            if (slotClicked == entry.getKey().index) {
                entry.getValue().performActions(player, event);
                return;
            }
        }
    }
    @Override
    public void closingMenu(Player player) {
        dynamicsItem.remove(player);
        readers.remove(player);
        onCloseMenu(player);
    }
    @Override
    public Player[] getReaders() {
        return new Player[0];
    }
    @Override
    public int getReaderAmount() {
        return 0;
    }

    protected void refreshMenu(Player player) {
        if (!readers.contains(player)) {
            openMenu(player);
            return;
        }
        for (Map.Entry<FurnacePlace, IGuiItem> entry : items.entrySet()) {
            player.getOpenInventory();
            player.getOpenInventory().setItem(entry.getKey().index, entry.getValue().getItem());
        }
        for (Map.Entry<FurnacePlace, IGuiItem> entry : dynamicsItem.get(player).entrySet()) {
            player.getOpenInventory().setItem(entry.getKey().index, entry.getValue().getItem());
        }
    }

    /**
     * actions
     */
    private void openMenu(Player player) {
        readers.add(player);
        dynamicsItem.put(player, new HashMap<>());
        onOpenMenu(player);
        refreshMenu(player);
    }
    protected void addItem(IGuiItem item, FurnacePlace place) {
        items.put(place, item);
    }
    protected void addItem(Player player, IGuiItem item, FurnacePlace place) throws CannotPlaceItemException {
        if (!dynamicsItem.containsKey(player))
            throw new CannotPlaceItemException();
        dynamicsItem.get(player).put(place, item);
    }

    /**
     * events
     */
    protected abstract void onOpenMenu(Player player);
    protected abstract void onCloseMenu(Player player);

    public enum FurnacePlace {
        SMELTING(0),
        FUEL(1),
        RESULT(2)
        ;
        private final int index;
        FurnacePlace(int index) {
            this.index = index;
        }
    }
}
