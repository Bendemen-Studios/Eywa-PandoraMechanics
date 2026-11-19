package com.bendemen.eywa.pandora.mechanics;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public final class PandoraTerritorialWarningMechanic implements INoTargetSkill {
    private final double radius;
    private final long cooldownMillis;
    private final String message;

    public PandoraTerritorialWarningMechanic(MythicLineConfig config) {
        radius = Math.max(1.0, config.getDouble("radius", 12.0));
        cooldownMillis = Math.max(0L, (long) (config.getDouble("cooldown", 6.0) * 1000L));
        message = config.getString("message", "&6You are too close to %mob%'s territory! Leave now!");
    }

    @Override
    public SkillResult cast(SkillMetadata data) {
        LivingEntity guardian = MechanicUtil.caster(data);
        if (guardian == null || !MechanicUtil.ready(guardian, "territorial-warning", cooldownMillis)) return SkillResult.SUCCESS;
        double squared = radius * radius;
        String mobName = guardian.getCustomName() == null ? guardian.getName() : guardian.getCustomName();
        var warning = LegacyComponentSerializer.legacyAmpersand().deserialize(message.replace("%mob%", mobName));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(guardian.getWorld())
                    && player.getLocation().distanceSquared(guardian.getLocation()) <= squared) {
                player.sendActionBar(warning);
            }
        }
        return SkillResult.SUCCESS;
    }
}
