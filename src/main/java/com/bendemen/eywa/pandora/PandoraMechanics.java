package com.bendemen.eywa.pandora;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public final class PandoraMechanics {
    private final EywaPandoraMechanics plugin;
    private final BukkitTask cleanupTask;

    PandoraMechanics(EywaPandoraMechanics plugin) {
        this.plugin = plugin;
        cleanupTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> { }, 1200L, 1200L);
    }

    void register(MythicMechanicLoadEvent event) {
        String name = event.getMechanicName();
        if (name.equalsIgnoreCase("PandoraPredatorAlarm")
                || name.equalsIgnoreCase("PandoraPreyPanic")
                || name.equalsIgnoreCase("PandoraTerritorialWarning")) {
            plugin.getLogger().warning("Requested mechanic " + name
                    + " is not registered yet: its MythicMobs 5.9.0 SkillMechanic adapter still needs API binding.");
        }
    }

    // Behaviour methods to be called by the version-specific MythicMobs SkillMechanic adapters.
    public void predatorAlarm(LivingEntity source) {
        source.getNearbyEntities(plugin.getConfig().getDouble("predator-alarm.radius", 24.0),
                plugin.getConfig().getDouble("predator-alarm.radius", 24.0),
                plugin.getConfig().getDouble("predator-alarm.radius", 24.0)).stream()
                .filter(LivingEntity.class::isInstance)
                .map(LivingEntity.class::cast)
                .filter(entity -> entity != source)
                .filter(entity -> !(entity instanceof Player))
                .forEach(entity -> entity.setTarget(entity.getTarget()));
    }

    public void preyPanic(LivingEntity prey) {
        double radius = plugin.getConfig().getDouble("prey-panic.radius", 16.0);
        prey.getNearbyEntities(radius, radius, radius).stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .findFirst()
                .ifPresent(player -> prey.setVelocity(prey.getLocation().toVector()
                        .subtract(player.getLocation().toVector()).normalize().multiply(0.65)));
    }

    public void territorialWarning(LivingEntity guardian) {
        double radius = plugin.getConfig().getDouble("territorial-warning.radius", 12.0);
        String message = plugin.getConfig().getString("territorial-warning.warning-message",
                "&6%mob% waarschuwt je: verlaat het territorium!");
        guardian.getNearbyEntities(radius, radius, radius).stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .forEach(player -> player.sendMessage(message.replace("%mob%", guardian.getName()).replace('&', '§')));
    }

    void shutdown() {
        cleanupTask.cancel();
    }
}
