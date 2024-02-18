```mermaid
classDiagram

class Entity
<<Abstract>> Entity
Entity : #copy()* Entity

class ExampleEntity
Entity <|-- ExampleEntity : override
ExampleEntity : #copy() ExampleEntity

```
