package com.bendemen.eywa.pandora;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class EywaPandoraMechanics extends JavaPlugin implements Listener {
    private PandoraMechanics mechanics;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (getServer().getPluginManager().getPlugin("MythicMobs") == null) {
            getLogger().severe("MythicMobs is required; disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        mechanics = new PandoraMechanics(this);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("EywaPandoraMechanics enabled.");
    }

    @Override
    public void onDisable() {
        if (mechanics != null) mechanics.shutdown();
    }

    @EventHandler
    public void onMechanicLoad(MythicMechanicLoadEvent event) {
        mechanics.register(event);
    }
}
