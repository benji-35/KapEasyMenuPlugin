package fr.kap35.kapeasymenu.menu.legacy;

import fr.kap35.kapeasymenu.Items.BasicItemsGui;
import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.IGuiItem;
import fr.kap35.kapeasymenu.Items.ItemGuiAction;
import fr.kap35.kapeasymenu.listeners.ItemActions;
import fr.kap35.kapeasymenu.menu.IGuiMenu;
import fr.kap35.kapeasymenu.menu.exception.CannotPlaceItemException;
import fr.kap35.kapeasymenu.menu.exception.MenuSizeException;
import fr.kap35.kapeasymenu.menu.exception.PageNotFoundException;
import fr.kap35.kapeasymenu.menu.exception.PlayerNotReaderException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public abstract class ChestPaginationMenu implements IGuiMenu {

    private final int pageSize;
    private final JavaPlugin plugin;
    private final String title;
    private final Inventory menu;
    private List<Map<Integer, IGuiItem>> pages;
    private Map<Player, Integer> readers;
    private Map<Player, Map<Integer, IGuiItem>> dynamicsItem;
    private boolean enabled;

    public ChestPaginationMenu(JavaPlugin plugin, String title, int menuSize) throws MenuSizeException {
        if (menuSize % 9 != 0) {
            throw new MenuSizeException("Menu must have a size can be dividing by 9");
        }
        this.pageSize = menuSize;
        this.plugin = plugin;
        this.title = title;
        this.menu = Bukkit.createInventory(null, this.pageSize, this.title);
        this.readers = new HashMap<>();
        this.pages = new ArrayList<>();
        this.pages.add(new HashMap<>());
        this.enabled = true;
        this.dynamicsItem = new HashMap<>();
    }

    @Override
    public final String getTitle() {
        return title;
    }
    @Override
    public final int getSize() {
        return pageSize;
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
        nextPage(player);
        onOpenMenu(player);
    }
    @Override
    public void checkAction(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slotClicked = event.getSlot();
        if (!readers.containsKey(player))
            return;

        int page = readers.get(player);
        if (dynamicsItem.containsKey(player)) {
            for (Map.Entry<Integer, IGuiItem> entry : dynamicsItem.get(player).entrySet()) {
                if (entry.getKey() == slotClicked) {
                    entry.getValue().performActions(player, event);
                    return;
                }
            }
        }
        for (Map.Entry<Integer, IGuiItem> entry : pages.get(page).entrySet()) {
            if (entry.getKey() == slotClicked) {
                entry.getValue().performActions(player, event);
                return;
            }
        }
    }
    @Override
    public void closingMenu(Player player) {
        dynamicsItem.remove(player);
        if (!readers.containsKey(player))
            return;
        onCloseMenu(player);
        readers.remove(player);
    }
    @Override
    public final Player[] getReaders() {
        return readers.keySet().toArray(new Player[0]);
    }
    @Override
    public final int getReaderAmount() {
        return readers.keySet().size();
    }

    protected JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * actions
     * */
    protected void addItem(IGuiItem item, int slot, int page) throws CannotPlaceItemException {
        for (int i = pages.size(); i <= page; i++) {
            pages.add(new HashMap<>());
        }
        pages.get(page).put(slot, item);
    }
    protected void addItem(Player player, IGuiItem item, int slot) throws PlayerNotReaderException, CannotPlaceItemException {
        addItem(player, item, slot, false);
    }
    private void addItem(Player player, IGuiItem item, int slot, boolean force) throws PlayerNotReaderException, CannotPlaceItemException {
        if (!readers.containsKey(player))
            throw new PlayerNotReaderException();
        if (!force || !dynamicsItem.containsKey(player)) {
            int previousItemSlot = pageSize - 9 + 3;
            int nextItemSlot = pageSize - 9 + 5;
            if (slot == previousItemSlot || slot== nextItemSlot)
                throw new CannotPlaceItemException();
        }
        dynamicsItem.get(player).put(slot, item);
    }
    protected void refreshPage(Player player) {
        int page = readers.get(player);
        Map<Integer, IGuiItem> items = pages.get(page);
        for (int i = 0; i < pageSize; i++) {
            player.getOpenInventory().setItem(i, null);
        }
        for (Map.Entry<Integer, IGuiItem> entry : items.entrySet()) {
            player.getOpenInventory().setItem(entry.getKey(), entry.getValue().getItem());
        }
        for (Map.Entry<Integer, IGuiItem> entry : dynamicsItem.get(player).entrySet()) {
            player.getOpenInventory().setItem(entry.getKey(), entry.getValue().getItem());
        }
    }
    protected void openPage(Player player, int page) {
        if (page >= pages.size())
            page = pages.size() - 1;
        if (page < 0)
            page = 0;
        boolean firstAdded = !readers.containsKey(player);
        readers.put(player, page);
        dynamicsItem.put(player, new HashMap<>());
        if (!firstAdded)
            onSwitchPage(player, page);
        placeRequestedItems(player, page);
        refreshPage(player);
    }

    /**
     *  events
     */
    protected abstract void onSwitchPage(Player player, int page);
    protected abstract void onCloseMenu(Player player);
    protected abstract void onOpenMenu(Player player);

    private void nextPage(Player player) {
        if (!readers.containsKey(player))
            readers.put(player, -1);
        int currentPage = readers.get(player) + 1;
        openPage(player, currentPage);
    }
    private void previousPage(Player player) {
        if (!readers.containsKey(player))
            readers.put(player, 1);
        int currentPage = readers.get(player) - 1;
        openPage(player, currentPage);
    }
    private void placeRequestedItems(Player player, int page) {
        GuiItem previousItem = new GuiItem(plugin, Material.ARROW)
                .setName("Previous Page")
                .setAmount(1)
                .setDisableEvent(true)
                .setAction(ItemActions.LEFT_CLICK, new ItemGuiAction() {
                    @Override
                    public void run(Player player, JavaPlugin plugin) {
                        player.sendMessage("Previous page....");
                        previousPage(player);
                    }
                });
        GuiItem nextItem = new GuiItem(plugin, Material.ARROW)
                .setName("Next Page")
                .setAmount(1)
                .setDisableEvent(true)
                .setAction(ItemActions.LEFT_CLICK, new ItemGuiAction() {
                    @Override
                    public void run(Player player, JavaPlugin plugin) {
                        player.sendMessage("Next page....");
                        nextPage(player);
                    }
                });

        for (int i = 0; i < 9; i++) {
            int index = (pageSize - 9) + i;
            IGuiItem itemToPlace;
            switch (i) {
                case 3:
                    if (page <= 0) {
                        itemToPlace = BasicItemsGui.emptySlotItem();
                    } else {
                        itemToPlace = previousItem;
                    }
                    break;
                case 5:
                    if (page >= pages.size() - 1) {
                        itemToPlace = BasicItemsGui.emptySlotItem();
                    } else {
                        itemToPlace = nextItem;
                    }
                    break;
                default:
                    itemToPlace = BasicItemsGui.emptySlotItem();
                    break;
            }
            dynamicsItem.get(player).put(index, itemToPlace);
        }
    }
}
