package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
    private Optional<State> savedState;
    private Optional<Section> saved;

    /**
     * Current playing section.
     */
    private Section currentSection;

    private final int mapRows;
    private final int mapCols;
    protected final Position playerDefaultSpawnPoint;

    public record SectionInstantiatedArgs(Event<EntityAddedArgs> onEntityAdded,
                                          Event<PlayerAddedArgs> onPlayerAdded) { }
    private Event<SectionInstantiatedArgs> onSectionInstantiated = new Event<>();

    public WorldImpl(int mapRows, int mapCols, Consumer<SectionInstantiatedArgs> bindings) {
        this.savedState = Optional.empty();
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        this.playerDefaultSpawnPoint = new Position((mapRows - 1) * 3/4, (mapCols - 1) / 2);
        this.onSectionInstantiated.subscribe(bindings);
        createNewSection();
    }

    private void createNewSection() {
        this.currentSection = new Section(this);
        this.onSectionInstantiated.invoke(
            new SectionInstantiatedArgs(this.currentSection.getEntityAddedEvent(), this.currentSection.getPlayerAddedEvent())
        );
        this.currentSection.generateEntities();
        this.currentSection.addPlayer(Player.getDefault(currentSection, this.playerDefaultSpawnPoint));
    }

    void createNewSection(Function<Section, Player> player) {
        this.currentSection.entityRemovedEventInvoke();
        this.currentSection = new Section(this);
        this.onSectionInstantiated.invoke(
            new SectionInstantiatedArgs(this.currentSection.getEntityAddedEvent(), this.currentSection.getPlayerAddedEvent())
        );
        this.currentSection.generateEntities();
        this.currentSection.addPlayer(player.apply(currentSection));
    }

    void loadSavedSection() {
        if (savedState.isPresent()) {
            this.currentSection.entityRemovedEventInvoke();
            this.currentSection = savedState.get().getSection();
            this.onSectionInstantiated.invoke(
                new SectionInstantiatedArgs(
                    this.currentSection.getEntityAddedEvent(), this.currentSection.getPlayerAddedEvent()
                )
            );
            this.currentSection.addPlayer(savedState.get().getPlayer());
        } else {
            throw new IllegalStateException("Cannot call switchToSection when player hasn't yet saved to an island");
        }
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
    public Optional<State> getSavedState() {
        return savedState;
    }

    @Override
    public void setSavedState() {
        final Player player = getCurrentSection().getPlayer().duplicate();
        savedState = Optional.of(new State(getCurrentSection(), player));
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