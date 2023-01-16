package fr.kap35.kapeasymenu.commands;

import fr.kap35.kapeasymenu.Menu.GuiManager;
import fr.kap35.kapeasymenu.Menu.GuiMenu;
import fr.kap35.kapeasymenu.Menu.IGuiMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class checkMenuExistsCommand implements CommandExecutor {
    GuiManager guiManager;
    public checkMenuExistsCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§cYou must specify a menu name");
            return false;
        }

        StringBuilder nameMenu = new StringBuilder();
        for (String arg : args) {
            nameMenu.append(arg).append(" ");
        }
        if (nameMenu.charAt(nameMenu.length() - 1) == ' ') {
            nameMenu.deleteCharAt(nameMenu.length() - 1);
        }

        if (sender instanceof ConsoleCommandSender) {
            giveAnswer(nameMenu.toString(), sender);
            return true;
        }
        if (sender instanceof Player && sender.hasPermission("kapeasymenu.menuExists")) {
            giveAnswer(nameMenu.toString(), sender);
            return true;
        }
        return false;
    }

    private void giveAnswer(String nameMenu, CommandSender sender) {
        if (guiManager.menuExists(nameMenu)) {
            IGuiMenu menu = guiManager.getMenu(nameMenu);
            sender.sendMessage("Menu \"" + nameMenu + "\" exists !");
            sender.sendMessage("Title : " + menu.getTitle());
            sender.sendMessage("Size : " + menu.getSize());
            sender.sendMessage("Items : " + menu.getItems().size());
        } else {
            sender.sendMessage("Menu \"" + nameMenu + "\" does not exists !");
        }
    }
}
