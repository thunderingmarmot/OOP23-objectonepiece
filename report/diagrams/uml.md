```mermaid
classDiagram
    class Entity
    <<abstract>> Entity
    Entity : -Position position
    Entity : -Direction direction

    class Collidable
    <<abstract>> Collidable

    class Collider
    <<abstract>> Collider
```
