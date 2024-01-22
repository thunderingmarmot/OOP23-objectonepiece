package it.unibo.object_onepiece.model;

/**
 * Everything present physically in the game (es player,barrel...)
 */
public interface Entity {
    public Section getSection();
    
    /**
     * The coordinate system used within
     * a section
     */
    public record Position(int x, int y) {};
    public Position getPosition();
}