package com.bendemen.eywa.pandora.mechanics;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public final class PandoraPredatorAlarmMechanic implements INoTargetSkill {
    private final double radius;
    private final long cooldownMillis;
    private final Sound sound;

    public PandoraPredatorAlarmMechanic(MythicLineConfig config) {
        radius = Math.max(1.0, config.getDouble("radius", 24.0));
        cooldownMillis = Math.max(0L, (long) (config.getDouble("cooldown", 5.0) * 1000L));
        Sound parsed;
        try { parsed = Sound.valueOf(config.getString("sound", "ENTITY_WOLF_GROWL").toUpperCase(java.util.Locale.ROOT)); }
        catch (IllegalArgumentException ex) { parsed = Sound.ENTITY_WOLF_GROWL; }
        sound = parsed;
    }

    @Override
    public SkillResult cast(SkillMetadata data) {
        LivingEntity predator = MechanicUtil.caster(data);
        if (predator == null || !MechanicUtil.ready(predator, "predator-alarm", cooldownMillis)) return SkillResult.SUCCESS;
        double squared = radius * radius;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(predator.getWorld())
                    && player.getLocation().distanceSquared(predator.getLocation()) <= squared) {
                player.playSound(predator.getLocation(), sound, 1.0f, 0.8f);
            }
        }
        return SkillResult.SUCCESS;
    }
}
