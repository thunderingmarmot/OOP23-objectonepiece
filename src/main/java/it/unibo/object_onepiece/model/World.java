package it.unibo.object_onepiece.model;

import java.util.List;
import it.unibo.object_onepiece.model.Utils.State;

/**
 * Represents World of the game, with the current section and the saved ones.
 */
public interface World {
    /**
     * 
     * @return State last saved section.
     */
    State getSavedState();
    /**
     * Save current section.
     */
    void setSavedState();
    /**
     * 
     * @return Player object in the section
     */
    Player getPlayer();
    /**
     * 
     * @return enemies present in the section
     */
    List<Enemy> getEnemies();
    /**
     * 
     * @return number of rows in a section
     */
    int getMapRows();
    /**
     * 
     * @return number of columns in a section
     */
    int getMapCols();
}
