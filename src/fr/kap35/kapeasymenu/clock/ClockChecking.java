package fr.kap35.kapeasymenu.clock;

import fr.kap35.kapeasymenu.KapEasyMenu;
import fr.kap35.kapeasymenu.Menu.GuiManager;
import org.bukkit.scheduler.BukkitRunnable;

public class ClockChecking extends BukkitRunnable {

    KapEasyMenu kapEasyMenu;
    GuiManager guiManager;

    public ClockChecking(KapEasyMenu kapEasyMenu, GuiManager guiManager) {
        this.kapEasyMenu = kapEasyMenu;
        this.guiManager = guiManager;
        this.runTaskTimer(kapEasyMenu, 0, 20);
    }

    @Override
    public void run() {
        guiManager.checkReaders();
    }
}
