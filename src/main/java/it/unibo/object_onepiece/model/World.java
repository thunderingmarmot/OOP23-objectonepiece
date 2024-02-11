package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.function.Consumer;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Utils.State;

/**
 * Represents World of the game, with the current section and the saved ones.
 */
public interface World {
    record EntityCreatedArgs(String name, Position spawnPosition, CardinalDirection spawnDirection) { }
    record EntityUpdatedArgs(String name, Position oldPosition, Position newPosition, CardinalDirection newDirection) { }
    record EntityRemovedArgs(Position lastPosition) { };
    record PlayerUpdatedArgs(List<Integer> healthList, List<Integer> maxHealthList, Integer experience) { };

    record Observers(
        Consumer<EntityCreatedArgs> createEntity, 
        Consumer<EntityUpdatedArgs> updateEntity,
        Consumer<EntityRemovedArgs> removeEntity,
        Consumer<PlayerUpdatedArgs> updatePlayerInfo) { }
    
    Observers getObservers();
    
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
