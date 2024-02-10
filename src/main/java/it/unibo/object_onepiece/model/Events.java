package it.unibo.object_onepiece.model;

import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.events.EventArgs.TriArguments;
import it.unibo.object_onepiece.model.events.EventArgs.QuadrArguments;

/**
 * Helper class to quickly declare and use events.
 * @see Event
 */
public final class Events {
    private Events() { };

    /**
     * An Event alias that is used when an Entity state is updated in any way.
     * @see Event
     * @see Entity
     */
    public static final class EntityUpdatedEvent
    extends Event<TriArguments<Optional<Position>, Optional<Position>, Optional<CardinalDirection>>> {
        /**
         * A less verbose version of invoke that directly takes the Event arguments.
         * @param oldPosition the position this Entity was before the update
         * @param newPosition the position this Entity is after the update
         * @param direction the direction this Entity is after the update
         * @see Event
         */
        protected void invoke(final Optional<Position> oldPosition,
                              final Optional<Position> newPosition,
                              final Optional<CardinalDirection> direction) {
            super.invoke(new TriArguments<>(oldPosition, newPosition, direction));
        }
    }

    /**
     * An Event alias that is used when an Entity is created in a Section.
     * @see Event
     * @see Section
     */
    public static final class EntityCreatedEvent
    extends Event<QuadrArguments<String, Position, Optional<CardinalDirection>, EntityUpdatedEvent>> {
        /**
         * A less verbose version of invoke that directly takes the Event arguments.
         * @param entityName the name of the Entity class that has been created
         * @param spawnPosition the position this Entity has been spawned at
         * @param spawnDirection the direction this Entity has been spawned in
         * @param entityUpdatedEvent the EntityUpdatedEvent of the newly created Entity
         * @see Event
         */
        protected void invoke(final String entityName,
                              final Position spawnPosition,
                              final Optional<CardinalDirection> spawnDirection,
                              final EntityUpdatedEvent entityUpdatedEvent) {
            super.invoke(new QuadrArguments<>(entityName, spawnPosition, spawnDirection, entityUpdatedEvent));
        }
    }

    /**
     * An Event alias that is used when a Section is created in World.
     * @see Event
     * @see World
     */
    public static final class SectionInstantiatedEvent
    extends Event<Argument<Section>> {
        /**
         * A less verbose version of invoke that directly takes the Event arguments.
         * @param section the Section object that has been newly instantiated
         * @see Event
         */
        protected void invoke(final Section section) {
            super.invoke(new Argument<>(section));
        }
    }
}
