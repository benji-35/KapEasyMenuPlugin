package fr.kap35.kapeasymenu.commands;

import fr.kap35.kapeasymenu.Menu.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OpenMenuCommand implements CommandExecutor, TabCompleter {
    GuiManager guiManager;

    public OpenMenuCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command !");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("kapeasymenu.openMenu")) {
            player.sendMessage("You don't have the permission to use this command !");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("You must specify a menu name !");
            return true;
        }
        StringBuilder menuName = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                menuName.append(args[i]).append(" ");
            } else {
                menuName.append(args[i]);
            }
        }
        if (!guiManager.menuExists(menuName.toString())) {
            player.sendMessage("This menu doesn't exist !");
            return true;
        }
        guiManager.openMenu(player, menuName.toString());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        String menuName = "";
        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                menuName += args[i] + " ";
            } else {
                menuName += args[i];
            }
        }
        ArrayList<String> allNames = guiManager.getAllMenuKeys();
        ArrayList<String> names = new ArrayList<>();
        for (String name : allNames) {
            if (name.toLowerCase().startsWith(menuName.toLowerCase())) {
                names.add(name);
            }
        }
        return names;
    }
}
