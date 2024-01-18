package fr.kap35.kapeasymenu.Interfaces;

import fr.kap35.kapeasymenu.menu.IGuiMenu;
import org.bukkit.entity.Player;

public interface IGuiManager {

    public void registerMenus(IGuiMenu menu, String name);
    public void openMenu(Player player, String name);

}
