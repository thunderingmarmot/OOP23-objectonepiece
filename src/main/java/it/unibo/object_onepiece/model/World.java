package it.unibo.object_onepiece.model;

import java.util.List;

/**
 * Represents World of the game, with the current section and the saved ones.
 */
public interface World {
    /**
     * 
     * @return Player object in the section
     */
    PlayerImpl getPlayer();
    /**
     * 
     * @return enemies present in the section
     */
    List<EnemyImpl> getEnemies();
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
