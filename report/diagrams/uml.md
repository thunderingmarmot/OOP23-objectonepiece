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
Player --|> Ship
Player : -int DEFAULT_EXPERIENCE_HEAL_COST$
Player : -int experience
Player : -Event~PlayerUpdatedArgs~ onPlayerUpdated
Player : #copy() Player
Player : +isInSamePositionAs(Position position) boolean
Player : +moveTo(Position destination) boolean
Player : +shootAt(Position target) boolean
Player : -getFromShipComponents(Function mapper) Stream~T~
%% The Function in getFromShipComponents is actually a Function<ShipComponent, T>, but Mermaid doesn't support more than one generic argument
Player : #getMaxHealths() List~int~
Player : #getHealths() List~int~
Player : #getNames() List~String~
Player : #getExperience() int
Player : #addExperience(int experienceToAdd) void
Player : #subtractExperience(int experienceToSubtract) void
Player : #healWithExperience() void
Player : #takeDamage(int damage, ShipComponent component) void
Player : #heal() void
Player : #die() void
Player : +getPlayerUpdatedEvent() Event~PlayerUpdatedArgs~

class Enemy
Enemy --|> Ship
EnemyState *-- Enemy

class EnemyState
<<Abstract>> EnemyState
EnemyState : #perform()*
EnemyState : #getState()*

class AttackState
AttackState --|> EnemyState
NavigationalSystem *-- AttackState

class ObstacleAvoidance
ObstacleAvoidance --|> EnemyState

class Patrol
Patrol --|> EnemyState
NavigationalSystem *-- Patrol

class NavigationalSystem
<<Inteface>> NavigationalSystem

class Compass
Compass --|> NavigationalSystem

class Section
Section *--* Entity

class World
<<Interface>> World

class WorldImpl
WorldImpl --* Section
World <|-- WorldImpl

class Position
<<Record>> Position
Position : +int row
Position : +int column

class CardinalDirection
<<Enumeration>> CardinalDirection

class Event
```
