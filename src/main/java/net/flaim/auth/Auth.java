package net.flaim.auth;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Auth extends JavaPlugin {
    private static Auth instance;

    public static Auth getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        new DataBase();

        BukkitScheduler scheduler = this.getServer().getScheduler();

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("log", new Commands())
        );

        scheduler.runTaskLater(this, () -> {
            this.getServer().broadcast(Component.text("Hello, World!"));
        }, 20);
    }

}
