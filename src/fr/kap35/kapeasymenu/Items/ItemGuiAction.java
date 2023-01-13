package fr.kap35.kapeasymenu.Items;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@FunctionalInterface
public interface ItemGuiAction {
    public void run(Player player, JavaPlugin plugin);

    public default void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
