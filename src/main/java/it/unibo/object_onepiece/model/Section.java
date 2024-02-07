package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.TriArguments;

/**
 * Section of the world which contains a list of entities present in it.
 */
public interface Section {
    /**
     * Allows access to world (usually used to allow an Island object to save the state of the game in World).
     * @return World 
     */
    World getWorld();
    /**
     * Gives a static class Bound which contains methods and functions useful for verifying if entities are inside grid.
     * @return Bound
     */
    Bound getBounds();
    /**
     * 
     * @param position of entity
     * @return Optional<Entity> at @param position or Optional.empty if there's none
     */
    Optional<Entity> getEntityAt(Position position);
    /**
     * 
     * @return list of entities present in section
     */
    List<Entity> getEntities();
    /**
     * 
     * @return Event for signalling the creation of entities in view
     */
    Event<TriArguments<Class<? extends Entity>, Position, Optional<CardinalDirection>>> getEntityCreatedEvent();
    /**
     * Calls the algorithm of entity generation and fills list of entities.
     */
    void generateEntities();
    /**
     * 
     * @param e entity to be added
     */
    void addEntity(Entity e);
    /**
     * Removes entity at @param position if it exists.
     * @param position where to remove entity
     */
    void removeEntityAt(Position position);
    /**
     * 
     * @return one and only Player that's in the section
     */
    PlayerImpl getPlayer();
}
