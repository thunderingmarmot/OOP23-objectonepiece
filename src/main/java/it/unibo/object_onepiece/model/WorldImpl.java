package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.function.Consumer;

import it.unibo.object_onepiece.model.Consumers.QuadrConsumer;
import it.unibo.object_onepiece.model.Consumers.TriConsumer;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Utils.State;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;

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

    /**
     * Make view subscribe to the section creation event.
     */
    private final SectionInstantiatedEvent onSectionInstantiated = new SectionInstantiatedEvent();

    private final int mapRows;

    private final int mapCols;

    

    /**
     * 
     * @param listener view method to associate for section instantiation
     */
    public WorldImpl(int mapRows, int mapCols, final Consumer<Argument<Section>> listener) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        onSectionInstantiated.subscribe(listener);
        createNewSection();
    }

    public WorldImpl(int mapRows, int mapCols, final Observers c) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
    }

    public record Observers(
        TriConsumer<String, Position, CardinalDirection> createEntity, 
        QuadrConsumer<String, Position, Position, CardinalDirection> updateEntity,
        Consumer<Position> removeEntity,
        TriConsumer<List<Integer>, List<Integer>, Integer> updatePlayerInfo) { }

    

    void createNewSection() {
        currentSection = new Section(this);
        onSectionInstantiated.invoke(currentSection);
        currentSection.generateEntities();
    }

    @Override
    public SectionInstantiatedEvent getSectionInstantiatedEvent() {
        return onSectionInstantiated;
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
