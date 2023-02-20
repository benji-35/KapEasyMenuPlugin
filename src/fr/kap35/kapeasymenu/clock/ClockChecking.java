package fr.kap35.kapeasymenu.clock;

import fr.kap35.kapeasymenu.KapEasyMenu;
import org.bukkit.scheduler.BukkitRunnable;

public class ClockChecking extends BukkitRunnable {

    KapEasyMenu kapEasyMenu;

    public ClockChecking(KapEasyMenu kapEasyMenu) {
        this.kapEasyMenu = kapEasyMenu;
        this.runTaskTimer(kapEasyMenu, 0, 20);
    }

    @Override
    public void run() {
        kapEasyMenu.getGuiManager().checkReaders();
    }
}
