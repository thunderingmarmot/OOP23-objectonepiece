package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;

/**
 * Models everything physically present in the game.
 */
public interface Entity {
    /**
     * Getter for the Position this Entity is at.
     * 
     * @return the Position of this Entity
     */
    Position getPosition();

    /**
     * Getter for the Event on position changed.
     * 
     * @return Event of position changed.
     */
    Event<BiArgument<Position>> getPositionChangedEvent();

    /**
     * Getter for the Event on entity removed.
     * 
     * @return Event of removed entity.
     */
    Event<Argument<Position>> getEntityRemovedEvent();
}
