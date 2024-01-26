package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Everything present physically in the game (es player,barrel...)
 */
public interface Entity {
    public Section getSection();
    
    /**
     * The coordinate system used within
     * a section
     */
    public Position getPosition();
}