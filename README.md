# Space Shooter libGDX
A simple 2D space shooter built with libGDX as part of a design patterns course project.
## Status
In progress  day 1

## Tech Stack
Java
libGDX

## Goal
Practice applying design patterns (Singleton, Factory, Observer) in a real game project.

### Singleton — `GameManager`
Manages global game state: score, lives, game over flag.
Only one instance can exist — accessed via `GameManager.getInstance()`.
This avoids passing state manually between screens and entities.

### Factory — `EnemyFactory`
Creates different enemy types (SLOW, FAST, TANK) through a single `create(type, x, y)` method.
The caller doesn't need to know construction details — just asks for a type.
### Observer — `GameEventListener` + `GameManager`
**Problem:** When an enemy dies, multiple parts of the game need to react
(update HUD, show feedback message, trigger game over).
Direct calls would create tight coupling between unrelated classes.

**Solution:** `GameManager` maintains a list of listeners.
Any class implementing `GameEventListener` can subscribe and react to events
without `GameManager` knowing who is listening.
## Project Structure
```
core/src/main/java/game/mygame/
├── SpaceShooter.java              # ApplicationListener entry point
├── GameManager.java               # Singleton — global state + event hub
├── GameScreen.java                # Main screen — implements GameEventListener
├── entities/
│   ├── Player.java                # Movement, shooting
│   ├── Enemy.java                 # Base enemy
│   └── Bullet.java                # Projectile
├── factory/
│   └── EnemyFactory.java          # Factory — creates enemy types
└── observer/
    ├── GameEventListener.java     # Observer interface
    └── GameEvent.java             # Event types enum
```
## Progress
  - [x] Project setup
 - [x] Player movement + shooting
- [x] Singleton: GameManager
- [x] Enemy base class
- [x] Factory: EnemyFactory
- [x] Observer: GameEvent and GameEventListener 
## Controls
| Key | Action |
|-----|--------|
| ← / A | Move left |
| → / D | Move right |
| Space | Shoot |

## How to Run
Open in IntelliJ run the lwjgl3
