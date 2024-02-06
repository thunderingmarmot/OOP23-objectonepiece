package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Models everything physically present in the game.
 */
public interface Entity {
    /**
     * Getter for the reference of the Section this Entity is inside of.
     * @return the Section of this Entity
     */
    Section getSection();

    /**
     * Getter for the Position this Entity is at.
     * @return the Position of this Entity
     */
    Position getPosition();
}
