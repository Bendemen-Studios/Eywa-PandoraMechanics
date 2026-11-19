package com.bendemen.eywa.pandora;

import com.bendemen.eywa.pandora.mechanics.PandoraPredatorAlarmMechanic;
import com.bendemen.eywa.pandora.mechanics.PandoraPreyPanicMechanic;
import com.bendemen.eywa.pandora.mechanics.PandoraTerritorialWarningMechanic;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class EywaPandoraMechanics extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (getServer().getPluginManager().getPlugin("MythicMobs") == null) {
            getLogger().severe("MythicMobs is required. Disabling EywaPandoraMechanics.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Listening for MythicMobs mechanic registration events.");
    }

    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event) {
        switch (event.getMechanicName().toLowerCase(java.util.Locale.ROOT)) {
            case "pandorapredatoralarm" ->
                    event.register(new PandoraPredatorAlarmMechanic(event.getConfig()));
            case "pandorapreypanic" ->
                    event.register(new PandoraPreyPanicMechanic(event.getConfig()));
            case "pandoraterritorialwarning" ->
                    event.register(new PandoraTerritorialWarningMechanic(event.getConfig()));
            default -> {
                // Not one of this plugin's mechanics.
            }
        }
    }
}
