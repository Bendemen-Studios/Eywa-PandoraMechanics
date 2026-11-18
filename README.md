# Eywa-PandoraMechanics

Initial plugin scaffold targeting Paper 26.3.0 and MythicMobs 5.9.0, based on the server version screenshot supplied for this task.

## Important status

This is a **development scaffold, not a finished installable fix**. It contains the Paper plugin bootstrap, configuration and behaviour helper methods, but the MythicMobs 5.9.0 custom SkillMechanic adapters have not yet been implemented or verified. At present, requests for the three Pandora mechanics are logged as not registered. This code therefore does **not** yet remove the CustomMechanic:NULL warnings.

## Planned mechanics

- PandoraPredatorAlarm — nearby predator alert behaviour.
- PandoraPreyPanic — prey flee response.
- PandoraTerritorialWarning — territorial warning to nearby players.

## Build target

- Paper 26.3.0
- MythicMobs 5.9.0 (reported plugin version)
- Java 21
- Gradle

Run `gradle build`; output is expected in `build/libs/`.

## Next implementation step

Implement concrete MythicMobs 5.9.0 SkillMechanic classes and bind them through the exact API exposed by the 5.9.0 Mythic-Dist artifact. Do not install this scaffold expecting the mechanics to work until that adapter exists and a successful build/server test has been performed.
