package it.unibo.object_onepiece.model;

import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.events.EventArgs.TriArguments;

public final class Events {
    private Events() { };

    public static class EntityUpdatedEvent
    extends Event<TriArguments<Optional<Position>, Optional<Position>, Optional<CardinalDirection>>> {
        protected void invoke(Optional<Position> oldPosition, Optional<Position> newPosition, Optional<CardinalDirection> direction) {
            super.invoke(new TriArguments<>(oldPosition, newPosition, direction));
        }
    }

    public static class EntityCreatedEvent
    extends Event<TriArguments<String, Position, Optional<CardinalDirection>>> {
        protected void invoke(String entityName, Position spawnPosition, Optional<CardinalDirection> spawnDirection) {
            super.invoke(new TriArguments<>(entityName, spawnPosition, spawnDirection));
        }
    }

    public static class SectionInstantiatedEvent
    extends Event<Argument<Section>> {
        protected void invoke(Section section) {
            super.invoke(new Argument<>(section));
        }
    }
}
