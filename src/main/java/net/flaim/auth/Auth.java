package net.flaim.auth;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.flaim.auth.commands.Login;
import net.flaim.auth.commands.Register;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
                event -> event.registrar().register("log", new Login())
        );
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("reg", new Register())
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Добро пожаловать на сервер!");
    }



}
