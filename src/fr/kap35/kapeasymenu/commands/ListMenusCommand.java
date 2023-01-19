package fr.kap35.kapeasymenu.commands;

import fr.kap35.kapeasymenu.Menu.GuiManager;
import fr.kap35.kapeasymenu.Menu.IGuiMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListMenusCommand implements CommandExecutor {

    GuiManager guiManager;
    private final int LENGTH_PAGE = 9;

    public ListMenusCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String s, String[] args) {
        if (!Sender.hasPermission("kapeasymenu.menuList")) {
            return false;
        }
        int page = 0;
        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                Sender.sendMessage("§cYou must specify a number !");
                return false;
            }
        }
        int start = page * LENGTH_PAGE;
        int end = start + LENGTH_PAGE;
        List<IGuiMenu> menus = guiManager.getMenus();
        Sender.sendMessage("§6§lKapEasyMenu §7- §eMenus list [" + page + "/" + (menus.size() / LENGTH_PAGE) + "]");
        for(int i = start; i < end; i++) {
            if(i >= menus.size()) {
                break;
            }
            IGuiMenu menu = menus.get(i);
            Sender.sendMessage("§6" + menu.getTitle() + "§7: size=" + menu.getSize() + "§7, " + menu.getItems().size() + "§7 items" + "§7, readers=" + menu.getReaderAmount());
        }
        return true;
    }
}
