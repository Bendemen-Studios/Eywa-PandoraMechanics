# Eywa-PandoraMechanics

Custom MythicMobs mechanics for EywaMC Pandora.

## Target versions
- Paper 26.3.0
- MythicMobs 5.9.0
- Java 25 toolchain (for the Paper 26.3 target)

## Mechanics

All three mechanics are no-target skills. The MythicMob caster is used as the source entity.

### Predator alarm
Plays a configurable warning sound to players within range.
```yaml
- PandoraPredatorAlarm{radius=24;cooldown=5;sound=ENTITY_WOLF_GROWL}
```

### Prey panic
Finds the nearest living entity within range and moves the prey away from it.
```yaml
- PandoraPreyPanic{radius=16;speed=0.8;cooldown=1.5}
```

### Territorial warning
Sends a message to players inside the warning radius.
```yaml
- PandoraTerritorialWarning{radius=12;cooldown=6;message="&6%mob% waarschuwt je: verlaat het territorium!"}
```

## Build and install
1. Run `gradle build`.
2. Copy `build/libs/Eywa-PandoraMechanics-1.0.0.jar` to `plugins/`.
3. Keep MythicMobs installed; this plugin declares it as a hard dependency.
4. Restart the server, then run `/mm reload`.

The mechanics are discovered through MythicMobs' annotation-based `CustomComponentRegistry`. Cooldowns are per caster and mechanic.

## Behaviour notes
- Predator alarm is an audio warning for nearby players; it does not make other mobs acquire targets.
- Prey panic currently treats the nearest living entity as a threat. It is not yet faction-aware.
- Territorial warning targets players only.
- The source is aligned with the published MythicMobs API documentation, but a successful build and live test against the exact server jar are still required before production use.
