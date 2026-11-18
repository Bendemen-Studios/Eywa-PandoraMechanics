package com.bendemen.eywa.pandora;

import io.lumine.mythic.core.skills.CustomComponentRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class EywaPandoraMechanics extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (getServer().getPluginManager().getPlugin("MythicMobs") == null) {
            getLogger().severe("MythicMobs is required. Disabling EywaPandoraMechanics.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        new CustomComponentRegistry(this, "com.bendemen.eywa.pandora.mechanics");
        getLogger().info("Registered Pandora mechanics with MythicMobs.");
    }
}
