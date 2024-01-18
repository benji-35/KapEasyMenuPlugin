package fr.kap35.kapeasymenu.menu.legacy;

import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.IGuiItem;
import fr.kap35.kapeasymenu.menu.IGuiMenu;
import fr.kap35.kapeasymenu.menu.exception.MenuSizeException;
import fr.kap35.kapeasymenu.menu.exception.PlayerNotReaderException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public abstract class ChestMenu implements IGuiMenu  {

    private String title;
    private int menuSize;
    private Inventory menu;
    private JavaPlugin plugin;
    private Map<Integer, IGuiItem> items;
    private Map<Player, Map<Integer, IGuiItem>> dynamicsItem;
    private List<Player> readers;
    private boolean enabled;

    public ChestMenu(JavaPlugin plugin, String title, int menuSize) throws MenuSizeException {
        if (menuSize % 9 != 0) {
            throw new MenuSizeException("Menu must have a size can be dividing by 9");
        }
        this.title = title;
        this.menuSize = menuSize;
        this.plugin = plugin;
        this.items = new HashMap<>();
        this.readers = new ArrayList<>();
        this.enabled = true;
        this.menu = Bukkit.createInventory(null, menuSize, title);
        this.dynamicsItem = new HashMap<>();
    }

    @Override
    public final String getTitle() {
        return title;
    }
    @Override
    public final int getSize() {
        return menuSize;
    }
    @Override
    public Set<IGuiItem> getItems() {
        return new HashSet<>();
    }
    @Override
    public void setEnable(boolean enable) {
        enabled = enable;
    }
    @Override
    public final boolean isEnable() {
        return enabled;
    }
    @Override
    public void openGUI(Player player) {
        player.openInventory(menu);
        openPage(player);
        onOpenMenu(player);
    }
    @Override
    public void checkAction(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slotClicked = event.getSlot();
        if (!readers.contains(player))
            return;
        if (dynamicsItem.containsKey(player)) {
            for (Map.Entry<Integer, IGuiItem> entry : dynamicsItem.get(player).entrySet()) {
                if (entry.getKey() == slotClicked) {
                    entry.getValue().performActions(player, event);
                    return;
                }
            }
        }
        for (Map.Entry<Integer, IGuiItem> entry : items.entrySet()) {
            if (entry.getKey() == slotClicked) {
                entry.getValue().performActions(player, event);
                return;
            }
        }
    }
    @Override
    public void closingMenu(Player player) {
        dynamicsItem.remove(player);
        if (!readers.contains(player))
            return;
        onCloseMenu(player);
        readers.remove(player);
    }
    @Override
    public final Player[] getReaders() {
        return readers.toArray(new Player[0]);
    }
    @Override
    public final int getReaderAmount() {
        return readers.size();
    }

    /**
     * actions
     */
    protected void refreshPage(Player player) {
        if (!readers.contains(player)) {
            onOpenMenu(player);
            return;
        }
        for (Map.Entry<Integer, IGuiItem> entry : items.entrySet()) {
            player.getOpenInventory().setItem(entry.getKey(), entry.getValue().getItem());
        }
        for (Map.Entry<Integer, IGuiItem> entry : dynamicsItem.get(player).entrySet()) {
            player.getOpenInventory().setItem(entry.getKey(), entry.getValue().getItem());
        }
    }
    protected void addItem(IGuiItem item, int slot) {
        items.put(slot, item);
    }
    protected void addItem(Player player, IGuiItem item, int slot) throws PlayerNotReaderException {
        if (!readers.contains(player) || !dynamicsItem.containsKey(player))
            throw new PlayerNotReaderException();
        dynamicsItem.get(player).put(slot, item);
    }
    private void openPage(Player player) {
        readers.add(player);
        dynamicsItem.put(player, new HashMap<>());
        refreshPage(player);
    }

    /**
     * events
     */
    protected abstract void onCloseMenu(Player player);
    protected abstract void onOpenMenu(Player player);
}
