```mermaid
classDiagram

class Player
<<Interface>> Player
World --* Player
Player : +moveTo(Position destination) boolean
Player : +shootAt(Position target) boolean
Player : +healWithExperience() void
Player : +respawn() void

class Enemy
<<Interface>> Enemy
World --* Enemy
Enemy : +goNext() void

class World
<<Interface>> World
World : +getPlayer() Player
World : +getEnemies() List~Enemy~
World : +getMapRows() int
World : +getMapCols() int
```
