package com.bendemen.eywa.pandora.mechanics;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import java.io.File;
import java.util.Comparator;

@MythicMechanic(author = "Bendemen Studios", name = "PandoraPreyPanic",
        description = "Makes Pandora prey flee from the nearest nearby living entity")
public final class PandoraPreyPanicMechanic extends SkillMechanic implements INoTargetSkill {
    private final double radius;
    private final double speed;
    private final long cooldownMillis;

    public PandoraPreyPanicMechanic(SkillExecutor manager, File file, String line, MythicLineConfig config) {
        super(manager, file, line, config);
        radius = Math.max(1.0, config.getDouble("radius", 16.0));
        speed = Math.max(0.1, config.getDouble("speed", 0.8));
        cooldownMillis = Math.max(0L, (long) (config.getDouble("cooldown", 1.5) * 1000L));
    }

    @Override
    public SkillResult cast(SkillMetadata data) {
        LivingEntity prey = MechanicUtil.caster(data);
        if (prey == null || !MechanicUtil.ready(prey, "prey-panic", cooldownMillis)) return SkillResult.SUCCESS;
        double squared = radius * radius;
        LivingEntity threat = prey.getNearbyEntities(radius, radius, radius).stream()
                .filter(LivingEntity.class::isInstance).map(LivingEntity.class::cast)
                .filter(entity -> entity != prey && entity.isValid() && !entity.isDead())
                .filter(entity -> entity.getLocation().distanceSquared(prey.getLocation()) <= squared)
                .min(Comparator.comparingDouble(entity -> entity.getLocation().distanceSquared(prey.getLocation())))
                .orElse(null);
        if (threat == null) return SkillResult.SUCCESS;
        Vector direction = prey.getLocation().toVector().subtract(threat.getLocation().toVector());
        direction.setY(Math.max(0.15, direction.getY()));
        if (direction.lengthSquared() > 0.0001) prey.setVelocity(direction.normalize().multiply(speed));
        return SkillResult.SUCCESS;
    }
}
