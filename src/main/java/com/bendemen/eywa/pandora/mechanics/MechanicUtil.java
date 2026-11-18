package com.bendemen.eywa.pandora.mechanics;

import io.lumine.mythic.api.skills.SkillMetadata;
import org.bukkit.entity.LivingEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class MechanicUtil {
    private static final Map<String, Long> COOLDOWNS = new ConcurrentHashMap<>();
    private MechanicUtil() {}

    static LivingEntity caster(SkillMetadata data) {
        if (data == null || data.getCaster() == null || data.getCaster().getEntity() == null) return null;
        if (!(data.getCaster().getEntity().getBukkitEntity() instanceof LivingEntity living)) return null;
        return living;
    }

    static boolean ready(LivingEntity entity, String mechanic, long cooldownMillis) {
        long now = System.currentTimeMillis();
        String key = entity.getUniqueId() + ":" + mechanic;
        Long previous = COOLDOWNS.put(key, now);
        return previous == null || now - previous >= cooldownMillis;
    }
}
