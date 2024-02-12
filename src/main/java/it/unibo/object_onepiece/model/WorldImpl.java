package it.unibo.object_onepiece.model;

import java.util.List;
import it.unibo.object_onepiece.model.Utils.State;

/**
 * Implementation of World interface.
 */
public final class WorldImpl implements World {
    /**
     * Saved State of game when player saved his state on an Island.
     */
    private State savedState;

    /**
     * Current playing section.
     */
    private Section currentSection;

    private final int mapRows;

    private final int mapCols;

    private final Observers observers;

    /**
     * 
     * @param c view methods to associate to model
     */
    public WorldImpl(int mapRows, int mapCols, final Observers observers) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        this.observers = observers;
        createNewSection();
    }

    @Override
    public Observers getObservers() {
        return this.observers;
    }

    void createNewSection() {
        currentSection = new Section(this);
        currentSection.generateEntities();
    }

    @Override
    public Player getPlayer() {
        return getCurrentSection().getPlayer();
    }

    @Override
    public List<Enemy> getEnemies() {
        return getCurrentSection().getEntities().stream()
            .filter((e) -> e instanceof Enemy)
            .map((e) -> (Enemy) e)
            .toList();
    }

    @Override
    public State getSavedState() {
        return savedState;
    }

    @Override
    public void setSavedState() {
        final Player player = getCurrentSection().getPlayer();
        savedState = new State(getCurrentSection(), player.getPosition(), player.getExperience());
    }

    Section getCurrentSection() {
        return currentSection;
    }

    void instantiateSection() {
        currentSection = new Section(this);
    }

    @Override
    public int getMapRows() {
        return mapRows;
    }
    
    @Override
    public int getMapCols() {
        return mapCols;
    }
}
