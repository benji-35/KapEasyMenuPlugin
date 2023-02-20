package fr.kap35.kapeasymenu.Menu;

import com.sun.org.apache.xpath.internal.operations.Equals;
import fr.kap35.kapeasymenu.Items.BasicItemsGui;
import fr.kap35.kapeasymenu.Items.GuiItem;
import fr.kap35.kapeasymenu.Items.IGuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GuiMenuPages implements IGuiMenu {
    List<Map<Integer, GuiItem>> pages = new ArrayList<>();
    Map<Player, List<Map<Integer, GuiItem>>> pagesTmp = new HashMap<>();
    Map<Player, Integer> readers = new HashMap<>();
    List<Player> switchingPage = new ArrayList<>();
    Inventory gui = null;
    String title;
    int size;
    boolean isStatic;
    protected JavaPlugin plugin;
    boolean isTmpAdding = false;
    boolean displayPageNumber = true;
    boolean isEnable = true;
    Player tmpPlayer = null;

    public GuiMenuPages(JavaPlugin plugin, String title, int size, boolean isStatic) {
        this.title = title;
        this.size = size + 9;
        this.isStatic = isStatic;
        this.plugin = plugin;
        gui = Bukkit.createInventory(null, this.size, title);
        pages.add(new HashMap<>()); //add first page automatically
        onInit();
    }

    public GuiMenuPages(JavaPlugin plugin, String title, int size) {
        this(plugin, title, size, true);
    }

    @Override
    public void addItem(IGuiItem item, int slot, int page) {
        if (!isTmpAdding) {
            int tmpPage = 0;
            while (pages.size() < page + 1) {
                pages.add(new HashMap<Integer, GuiItem>());
                Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Page " + tmpPage + " not found, creating it. Creating until page " + page);
                tmpPage++;
            }
            if (pages.get(page).containsKey(slot)) {
                Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Slot already used, replacing item");
                pages.get(page).remove(slot);
            }
            pages.get(page).put(slot, (GuiItem) item);
        } else {
            if (pagesTmp.containsKey(tmpPlayer)) {
                int tmpPage = 0;
                while (pagesTmp.get(tmpPlayer).size() < page + 1) {
                    pagesTmp.get(tmpPlayer).add(new HashMap<Integer, GuiItem>());
                    Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Page " + tmpPage + " not found, creating it. Creating until page " + page);
                    tmpPage++;
                }
                pagesTmp.get(tmpPlayer).get(page).put(slot, (GuiItem) item);
            } else {
                pagesTmp.put(tmpPlayer, new ArrayList<>());
                int tmpPage = 0;
                while (pagesTmp.get(tmpPlayer).size() < page + 1) {
                    pagesTmp.get(tmpPlayer).add(new HashMap<Integer, GuiItem>());
                    Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Page " + tmpPage + " not found, creating it. Creating until page " + page);
                    tmpPage++;
                }
                pagesTmp.get(tmpPlayer).get(page).put(slot, (GuiItem) item);
            }
        }
    }

    @Override
    public void addItem(IGuiItem item, int slot) {
        addItem(item, slot, 0);
    }

    @Override
    public Collection<IGuiItem> getItems() {
        Collection<IGuiItem> items = new ArrayList<>();
        for (Map<Integer, GuiItem> page : pages) {
            items.addAll(page.values());
        }
        return items;
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
    public Player[] getReaders() {
        return readers.keySet().toArray(new Player[0]);
    }

    @Override
    public void openGUI(Player player) {
        if (!readers.containsKey(player)) {
            readers.put(player, 0);
        }
        gui.clear();
        int page = readers.get(player);
        if (page >= pages.size()) {
            page = pages.size() - 1;
            readers.put(player, page);
        } else if (page < 0) {
            page = 0;
            readers.put(player, page);
        }
        for (Map.Entry<Integer, GuiItem> entry : pages.get(page).entrySet()) {
            gui.setItem(entry.getKey(), entry.getValue().getItem());
        }
        for (int i = size - 9; i < size; i++) {
            gui.setItem(i, BasicItemsGui.emptySlotItem().getItem());
            if (i == size - 9 + 3) {
                if (page > 0) {
                    gui.setItem(i, BasicItemsGui.previousItem(plugin).getItem());
                }
            } else if (i == size - 9 + 5) {
                if (page + 1 < getTotPages(player)) {
                    gui.setItem(i, BasicItemsGui.nextItem(plugin).getItem());
                }
            }
        }
        if (isStatic) {
            if (displayPageNumber) {
                gui.setItem(size - 9 + 4, BasicItemsGui.pageNumberItem(page + 1, getTotPages(player)).getItem());
            }
            player.openInventory(gui);
            return;
        }
        Inventory guiTmp = Bukkit.createInventory(null, size, title);
        for (Map.Entry<Integer, GuiItem> entry : pages.get(page).entrySet()) {
            guiTmp.setItem(entry.getKey(), entry.getValue().getItem());
        }
        isTmpAdding = true;
        onOpenMenu(player);
        isTmpAdding = false;
        for (Map.Entry<Integer, GuiItem> entry : pagesTmp.get(readers.get(player)).get(page).entrySet()) {
            guiTmp.setItem(entry.getKey(), entry.getValue().getItem());
        }
        for (int i = size - 9; i < size; i++) {
            guiTmp.setItem(i, BasicItemsGui.emptySlotItem().getItem());
            if (i == size - 9 + 3) {
                if (page > 0) {
                    guiTmp.setItem(i, BasicItemsGui.previousItem(plugin).getItem());
                }
            } else if (i == size - 9 + 5) {
                if (page + 1 < getTotPages(player)) {
                    guiTmp.setItem(i, BasicItemsGui.nextItem(plugin).getItem());
                }
            }
        }
        if (displayPageNumber) {
            guiTmp.setItem(size - 9 + 4, BasicItemsGui.pageNumberItem(page + 1, getTotPages(player)).getItem());
        }
        player.openInventory(guiTmp);
    }

    @Override
    public void openGUI(Player player, int page) {
        if (page < 0)
            page = 0;
        if (page >= pages.size())
            page = pages.size() - 1;
        readers.remove(player);
        readers.put(player, page);
        openGUI(player);
    }

    @Override
    public void checkAction(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int page = 0;
        if (readers.containsKey(player)) {
            page = readers.get(player);
        }
        int slot = event.getSlot();
        if (page < 0 || page >= pages.size()) {
            Bukkit.getConsoleSender().sendMessage("§c[§4KapEasyMenu§c] §4Page not found");
            player.closeInventory();
            return;
        }
        if (slot == size - 9 + 3 && page > 0) {
            previousPage(player);
            return;
        } else if (slot == size - 9 + 5 && page < pages.size() - 1) {
            nextPage(player);
            return;
        } else if (slot >= size - 9) {
            event.setCancelled(true);
        }
        if (pages.get(page).containsKey(slot)) {
            pages.get(page).get(slot).performActions(player, event);
        }
    }

    @Override
    public void onOpenMenu(Player player) {
        if (!readers.containsKey(player)) {
            __addReader(player);
        }
    }

    @Override
    public void onCloseMenu(Player player) {
        if (!switchingPage.contains(player)) {
            __removeReader(player);
        } else {
            switchingPage.remove(player);
        }
    }

    @Override
    public void onInit() {

    }

    @Override
    public void setEnable(boolean enable) {
        this.isEnable = enable;
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    public void nextPage(Player player) {
        int page = 0;
        if (readers.containsKey(player)) {
            page = readers.get(player);
        }
        if (page < getTotPages(player)) {
            page++;
            readers.put(player, page);
            openGUI(player);
        }
        switchingPage.add(player);
    }

    public void previousPage(Player player) {
        int page = 0;
        if (readers.containsKey(player)) {
            page = readers.get(player);
        }
        if (page > 0) {
            page--;
            readers.put(player, page);
            openGUI(player);
        }
        switchingPage.add(player);
    }

    private int getTotPages(Player player) {
        int tot = pages.size();
        if (pagesTmp.containsKey(player)) {
            if (pagesTmp.get(player).size() > tot) {
                tot = pagesTmp.get(player).size();
            }
        }
        return tot;
    }

    public int getOpenPage(Player player) {
        int page = 0;
        if (readers.containsKey(player)) {
            page = readers.get(player);
        }
        return page;
    }

    public void setDisplayPageNumber(boolean displayPageNumber) {
        this.displayPageNumber = displayPageNumber;
    }

    @Override
    public boolean Equals(Object obj) {
        if (obj instanceof IGuiMenu) {
            IGuiMenu menu = (IGuiMenu) obj;
            return menu.getTitle().equals(title) && menu.getSize() == size;
        }
        return false;
    }

    @Override
    public void __removeReader(Player player) {
        readers.remove(player);
        pagesTmp.remove(player);
    }

    @Override
    public void __addReader(Player player) {
        readers.put(player, 0);
    }
}
