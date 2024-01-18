package fr.kap35.kapeasymenu.commands;

import fr.kap35.kapeasymenu.menu.GuiManager;
import fr.kap35.kapeasymenu.menu.IGuiMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetActiveMenuCommand implements CommandExecutor, TabCompleter {

    GuiManager guiManager;

    public SetActiveMenuCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /setmenuactive <menuName> <true/false>");
            return true;
        }
        StringBuilder menuName = new StringBuilder();
        for (int i = 0; i < (args.length - 1); i++) {
            if (i < args.length - 2) {
                menuName.append(args[i]).append(" ");
            } else {
                menuName.append(args[i]);
            }
        }
        IGuiMenu menu = guiManager.getMenu(menuName.toString());
        if (menu == null) {
            sender.sendMessage("§cMenu " + menuName + " not found !");
            return true;
        }
        if (args[args.length - 1].equalsIgnoreCase("true")) {
            guiManager.setMenuEnable(menuName.toString(), true);
            sender.sendMessage("§aMenu " + menuName + " is now active !");
        } else if (args[args.length - 1].equalsIgnoreCase("false")) {
            guiManager.setMenuEnable(menuName.toString(), false);
            sender.sendMessage("§aMenu " + menuName + " is now inactive !");
        } else {
            sender.sendMessage("§cUsage: /setmenuactive <menuName> <true/false>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 0) {
            for (IGuiMenu menu : guiManager.getMenus()) {
                result.add(menu.getTitle());
            }
        } else {
            String completeArgs = "";
            String totalArgs = "";
            for (int i = 0; i < args.length - 1; i++) {
                if (i < args.length - 2) {
                    completeArgs += args[i] + " ";
                } else {
                    completeArgs += args[i];
                }
            }
            totalArgs = completeArgs + args[args.length - 1];

            List<IGuiMenu> menusTotal = new ArrayList<>();
            List<IGuiMenu> menusComplete = new ArrayList<>();
            for (IGuiMenu menu : guiManager.getMenus()) {
                if (menu.getTitle().toLowerCase().startsWith(totalArgs.toLowerCase())) {
                    menusTotal.add(menu);
                }
            }
            for (IGuiMenu menu : guiManager.getMenus()) {
                if (menu.getTitle().toLowerCase().startsWith(completeArgs.toLowerCase())) {
                    menusComplete.add(menu);
                }
            }
            if (menusTotal.size() >= 1) {
                if (menusTotal.size() == 1) {
                    result.add("true");
                    result.add("false");
                } else {
                    for (IGuiMenu menu : menusTotal) {
                        result.add(menu.getTitle());
                    }
                }
            } else if (menusComplete.size() >= 1) {
                for (IGuiMenu menu : menusComplete) {
                    result.add(menu.getTitle());
                }
            } else {
                result.add("true");
                result.add("false");
            }
        }
        return result;
    }
}
