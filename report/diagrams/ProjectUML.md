```mermaid
classDiagram

class Entity
<<Abstract>> Entity
Entity : -Position position
Entity --* Position
Entity : -CardinalDirection direction
Entity --* CardinalDirection
Entity : -Section section
Entity --* Event
Entity : -Event~EntityCreatedArgs~ onEntityCreated
Entity : -Event~EntityUpdatedArgs~ onEntityUpdated
Entity : -Event~EntityRemovedArgs~ onEntityRemoved
Entity : #copy()* Entity
Entity : #getWorld() WorldImpl
Entity : #getSection() Section
Entity : #getPosition() Position
Entity : #getDirection() CardinalDirection
Entity : #setSection(Section newSection) void
Entity : #setPosition(Position newPosition) void
Entity : #setDirection(CardinalDirection newDirection) void
Entity : #remove() void
Entity : +getEntityCreatedEvent() Event~EntityCreatedArgs~
Entity : +getEntityUpdatedEvent() Event~EntityUpdatedArgs~
Entity : +getEntityRemovedEvent() Event~EntityRemovedArgs~

class Collidable
<<Abstract>> Collidable
Collidable --|> Entity
Collidable --* Rigidness
Collidable : #getRigidness()* Rigidness
Collidable : #onCollisionWith(Collider collider)* void

class Rigidness
<<Enumeration>> Rigidness
Rigidness : SOFT
Rigidness : MEDIUM
Rigidness : HARD

class Barrel
Barrel --|> Collidable
Barrel : -int DEFAULT_EXPERIENCE_GIVEN$
Barrel : -int experienceGiven
Barrel : #getExperienceGiven() int
Barrel : #onPickup(Player player) void
Barrel : #copy() Barrel
Barrel : #getRigidness() Rigidness
Barrel : #onCollisionWith(Collider collider) void

class Island
Island --|> Collidable
Island : #save() void
Island : #heal(Player player) void
Island : #copy() Island
Island : #getRigidness() Rigidness
Island : #onCollisionWith(Collider collider) void

class NavalMine
NavalMine --|> Collidable
NavalMine : -int DEFAULT_DAMAGE$
NavalMine : -int damage
NavalMine : #copy() NavalMine
NavalMine : #getRigidness() Rigidness
NavalMine : #onCollisionWith(Collider collider) void

class Collider
<<Abstract>> Collider
Collider --|> Collidable
Collider : #collideWith(Collidable collidable)* void

class Ship
<<Abstract>> Ship
Ship --|> Collider
Ship : -Weapon weapon
Ship --* Weapon
Ship : -Sail sail
Ship --* Sail
Ship : -Bow bow
Ship --* Bow
Ship : -Keel keel
Ship --* Keel
Ship : -List~MoveDetails~ MOVE_SUCCESS_CONDITIONS$
Ship : -List~MoveDetails~ MOVE_COLLISION_CONDITIONS$
MoveDetails *-- Ship
ShootDetails *-- Ship
Ship : #move(CardinalDirection direction, int steps) MoveDetails
Ship : #step(CardinalDirection direction) MoveDetails
Ship : #checkMove(CardinalDirection direction, Optional~Entity~ obstacle) MoveDetails
Ship : #rotate(CardinalDirection direction) void
Ship : #shoot(Position position) ShootDetails
Ship : -hitTarget(Position position, int damage) void
Ship : -getEntityPositionInTrajectory(Position position) Position
Ship : #takeDamage(int damage, ShipComponent component) void
Ship : #isShipDead() boolean
Ship : #die() void
Ship : #getShipComponents() List~ShipComponent~
Ship : #getWeapon() Weapon
Ship : #getSail() Sail
Ship : #getBow() Bow
Ship : #getKeel() Keel
Ship : #setWeapon(Weapon newWeapon)
Ship : #setSail(Sail newSail)
Ship : #setBow(Bow newBow)
Ship : #setKeel(Keel newKeel)
Ship : #copy() Ship
Ship : #collideWith(Collidable collidable) void
Ship : #getRigidness() Rigidness
Ship : #onCollisionWith(Collider collider) void

class MoveDetails
<<Enumeration>> MoveDetails
MoveDetails : HARD_COLLISION
MoveDetails : MEDIUM_COLLISION
MoveDetails : BORDER_REACHED
MoveDetails : ROTATED
MoveDetails : MOVED_BUT_COLLIDED
MoveDetails : MOVED_SUCCESSFULLY
MoveDetails : OUT_OF_SPEED_RANGE
MoveDetails : SAIL_BROKEN
MoveDetails : NO_MOVEMENT

class ShootDetails
<<Enumeration>> ShootDetails
ShootDetails : SHOOTED_SUCCESSFULLY
ShootDetails : OUT_OF_SHOOTING_RANGE
ShootDetails : WEAPON_BROKEN

class ShipComponent
<<Abstract>> ShipComponent
ShipComponent : -int maxHealth
ShipComponent : -int health
ShipComponent : #copy()* ShipComponent
ShipComponent : #getHealth() int
ShipComponent : #getMaxHealth() int
ShipComponent : #setHealth(int newHealth)

class Weapon
Weapon --|> ShipComponent
Weapon : -int maxDamage
Weapon : -int minDamage
Weapon : -int attackRange
Weapon : #copy() Weapon
Weapon : #getMaxDamage() int
Weapon : #getMinDamage() int
Weapon : #getRange() int
Weapon : #cannon()$ Weapon
Weapon : #railgun()$ Weapon
Weapon : #heavycannon()$ Weapon

class Sail
Sail --|> ShipComponent
Sail : -int maxSpeed
Sail : -int minSpeed
Sail : -boolean rotationPower
Sail : #copy() Sail
Sail : #isInSpeedRange() boolean
Sail : #getMaxSpeed() int
Sail : #getMinSpeed() int
Sail : #haveRotationPower() boolean
Sail : #sloop()$ Sail
Sail : #spinnaker()$ Sail
Sail : #schooner()$ Sail

class Bow
Bow --|> ShipComponent
Bow : -int BROKEN_CRASH_DAMAGE$
Bow : -int crashDamage
Bow : -int crashDamageMultiplier
Bow : #copy() Bow
Bow : #getCrashDamage() int
Bow : #getCrashDamageMultiplier() int
Bow : #standard()$ Bow
Bow : #heavy()$ Bow
Bow : #light()$ Bow

class Keel
Keel --|> ShipComponent
Keel : -int damageEndurance
Keel : #copy() Keel
Keel : #isKeelDamaged() boolean
Keel : #getDamageEndurance() int
Keel : #standard()$ Keel
Keel : #heavy()$ Keel
Keel : #light()$ Keel

class Player
<<Interface>> Player
Player : +moveTo(Position destination) boolean
Player : +shootAt(Position target) boolean
Player : +healWithExperience() void

class PlayerImpl
Player <|-- PlayerImpl
PlayerImpl --|> Ship
PlayerImpl : -int DEFAULT_EXPERIENCE_HEAL_COST$
PlayerImpl : -int experience
PlayerImpl : -Event~PlayerUpdatedArgs~ onPlayerUpdated
PlayerImpl : #copy() PlayerImpl
PlayerImpl : +isInSamePositionAs(Position position) boolean
PlayerImpl : +moveTo(Position destination) boolean
PlayerImpl : +shootAt(Position target) boolean
PlayerImpl : -getFromShipComponents(Function mapper) Stream~T~
%% The Function in getFromShipComponents is actually a Function<ShipComponent, T>, but Mermaid doesn't support more than one generic argument
PlayerImpl : #getMaxHealths() List~int~
PlayerImpl : #getHealths() List~int~
PlayerImpl : #getNames() List~String~
PlayerImpl : #getExperience() int
PlayerImpl : #addExperience(int experienceToAdd) void
PlayerImpl : #subtractExperience(int experienceToSubtract) void
PlayerImpl : #healWithExperience() void
PlayerImpl : #takeDamage(int damage, ShipComponent component) void
PlayerImpl : #heal() void
PlayerImpl : #die() void
PlayerImpl : +getPlayerUpdatedEvent() Event~PlayerUpdatedArgs~

class Enemy
<<Interface>> Enemy
Enemy : +goNext() void

class EnemyImpl
Enemy <|-- EnemyImpl
EnemyImpl --|> Ship
EnemyState *-- EnemyImpl
EnemyImpl : -List~MoveDetails~ ACTION_SUCCESS_CONDITIONS$
EnemyImpl : -int DEFAULT_TRIGGET_DISTANCE$
EnemyImpl : -int triggerDistance
EnemyImpl : -EnemyState currentState
EnemyImpl : -List~MoveDetails~ enemyStates
EnemyImpl : #copy() EnemyImpl
EnemyImpl : #getTriggerDistance() int
EnemyImpl : goNext() void
EnemyImpl : getCurrentState() States
EnemyImpl : changeState(States state) void
EnemyImpl : findState(States state) EnemyState

class States
<<Enumeration>> States
States : PATROLLING
States : AVOIDING
States : ATTACKING

class EnemyState
<<Abstract>> EnemyState
States *-- EnemyState
EnemyState : #perform()* boolean
EnemyState : #getState()* States

class AttackState
AttackState --|> EnemyState
NavigationSystem *-- AttackState
AttackState : -Enemy ship
AttackState : -NavigationSystem navigationSystem
AttackState : -Position objective
AttackState : #perform() boolean
AttackState : #getState() States
AttackState : -circularTargetPlayer() void
AttackState : -randSign() int

class ObstacleAvoidance
ObstacleAvoidance --|> EnemyState
ObstacleAvoidance : -Enemy ship
ObstacleAvoidance : -CardinalDirection avoidanceDirection
ObstacleAvoidance : #perform() boolean
ObstacleAvoidance : #getState() States

class Patrol
Patrol --|> EnemyState
NavigationSystem *-- Patrol
Patrol : -NavigationSystem compass
Patrol : -Enemy ship
Patrol : -Bound bound
Patrol : -States state
Patrol : -Position objective
Patrol : #perform() boolean
Patrol : #getState() States
Patrol : -checkPlayer() boolean
Patrol : -playerPos() Position
Patrol : -defineRandomObjective() void

class NavigationSystem
<<Interface>> NavigationSystem
NavigationSystem : +move(Position objective, Position current) CardinalDirection

class Compass
Compass --|> NavigationSystem
Compass : -Map DIR_MAP$
%% The Map DIR_MAP is actually a Map<Position, Supplier<CardinalDirection>>, but Mermaid doesn't support more than one generic argument
Compass : +move(Position objective, Position current) CardinalDirection

class Section
Section *--* Entity
Section : -double SCALING_FACTOR$
Section : -int NOISE_DISPERSION
Section : -int INSET_FACTOR
Section : -int rowInset
Section : -int colInset
Section : -int genAreaCols
Section : -int genAreaRows
Section : -WorldImpl world
Section : -List~Entity~ entities
Section : -Bound bound
Section : -Map getEntity
%% The Map getEntity is actually a Map<Integer, BiFunction<Position, CardinalDirection, Entity>>, but Mermaid doesn't support more than one generic argument
Section : -Event~EntityAddedArgs~ onEntityAdded
Section : -Event~PlayerAddedArgs~ onPlayerAdded
Section : #generateEntities() void
Section : #setEntities(List~Entity~ entities) void
Section : #clearEntities() void
Section : #getWorld() WorldImpl
Section : #getBounds() Bound
Section : #removeEntityAt(Position position) void
Section : #getPlayer() Player
Section : #getEntityAt(Position position) Optional~Entity~
Section : #getEntities() List~Entity~
Section : #addPlayer(Player player) void
Section : #addEntity(Entity entity) void
Section : +getEntityAddedEvent() Event~EntityAddedArgs~
Section : +getPlayerAddedEvent() Event~PlayerAddedArgs~

class World
<<Interface>> World
World : +getPlayer() Player
World : +getEnemies() List~Enemy~
World : +getMapRows() int
World : +getMapCols() int

class SavedSection
<<Record>> SavedSection
SavedSection : +List~Entity~ entities
SavedSection : +Player player

class WorldImpl
WorldImpl --* Section
World <|-- WorldImpl
SavedSection *-- WorldImpl 
WorldImpl : -Optional~SavedSection~ savedSection
WorldImpl : -Section currentSection
WorldImpl : -int mapRows
WorldImpl : -int mapCols
WorldImpl : -Position playerDefaultSpawnPoint
WorldImpl : -Event~SectionInstantiatedArgs~ onSectionInstantiated
WorldImpl : -createNewSection() void
WorldImpl : #createNewSection(Function player) void
%% The Function player is actually a Function<Section, Player>, but Mermaid doesn't support more than one generic argument
WorldImpl : #loadSavedSection() void
WorldImpl : +getPlayer() Player
WorldImpl : +getEnemies() List~Enemy~
WorldImpl : #getSavedState() Optional~SavedSection~
WorldImpl : #setSavedState() void
WorldImpl : #getCurrentSection() Section
WorldImpl : #instantiateSection() void
WorldImpl : +getMapRows() int
WorldImpl : +getMapCols() int
WorldImpl : #getPlayerDefaultSpawnPoint() Position

class Position
<<Record>> Position
Position : +int row
Position : +int column

class CardinalDirection
<<Enumeration>> CardinalDirection
```
