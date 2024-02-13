package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.function.Consumer;

import it.unibo.object_onepiece.model.Section.EntityAddedArgs;
import it.unibo.object_onepiece.model.Section.PlayerAddedArgs;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Utils.State;
import it.unibo.object_onepiece.model.events.Event;
import java.util.Optional;

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

    public record SectionInstantiatedArgs(Event<EntityAddedArgs> onEntityAdded,
                                          Event<PlayerAddedArgs> onPlayerAdded) { }
    private Event<SectionInstantiatedArgs> onSectionInstantiated = new Event<>();

    public WorldImpl(int mapRows, int mapCols, Consumer<SectionInstantiatedArgs> bindings) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        onSectionInstantiated.subscribe(bindings);
        createNewSection(Optional.empty());
    }

    void createNewSection(Optional<Player> p) {
        currentSection = new Section(this);
        onSectionInstantiated.invoke(new SectionInstantiatedArgs(
        currentSection.getEntityAddedEvent(), currentSection.getPlayerAddedEvent()));
        currentSection.generateEntities(p);
    }

    void switchSection(Section s, Player p) {
        this.currentSection = s;
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