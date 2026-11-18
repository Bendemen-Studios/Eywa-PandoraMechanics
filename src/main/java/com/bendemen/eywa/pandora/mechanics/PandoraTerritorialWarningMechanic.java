package com.bendemen.eywa.pandora.mechanics;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.io.File;

@MythicMechanic(author = "Bendemen Studios", name = "PandoraTerritorialWarning",
        description = "Warns nearby players about a territorial Pandora creature")
public final class PandoraTerritorialWarningMechanic extends SkillMechanic implements INoTargetSkill {
    private final double radius;
    private final long cooldownMillis;
    private final String message;

    public PandoraTerritorialWarningMechanic(SkillExecutor manager, File file, String line, MythicLineConfig config) {
        super(manager, file, line, config);
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
        String warning = ChatColor.translateAlternateColorCodes('&', message.replace("%mob%", mobName));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(guardian.getWorld())
                    && player.getLocation().distanceSquared(guardian.getLocation()) <= squared) {
                player.sendMessage(warning);
            }
        }
        return SkillResult.SUCCESS;
    }
}
