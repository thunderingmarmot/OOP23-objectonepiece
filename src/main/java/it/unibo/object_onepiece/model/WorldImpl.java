package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import it.unibo.object_onepiece.model.Section.EntityAddedArgs;
import it.unibo.object_onepiece.model.Section.PlayerAddedArgs;
import it.unibo.object_onepiece.model.Utils.Position;

import java.util.Optional;

/**
 * Implementation of World interface.
 */
public final class WorldImpl implements World {
    /**
     * Record for the section instantiation concerning events.
     * @param onEntityAdded
     * @param onPlayerAdded
     */
    public record SectionInstantiatedArgs(Event<EntityAddedArgs> onEntityAdded,
                                          Event<PlayerAddedArgs> onPlayerAdded) { }
    /**
     * Saved section of game when player saved his stats and position on an Island.
     */
    private Optional<Section> saved;
    /**
     * Current playing section.
     */
    private Section currentSection;
    private final int mapRows;
    private final int mapCols;
    private final Position playerDefaultSpawnPoint;
    private final Event<SectionInstantiatedArgs> onSectionInstantiated = new Event<>();
    /**
     * Creates a world (an abstraction that contains sections).
     * @param mapRows rows of the section
     * @param mapCols columns of the section
     * @param bindings view bindings for events
     */
    public WorldImpl(final int mapRows, final int mapCols, final Consumer<SectionInstantiatedArgs> bindings) {
        this.saved = Optional.empty();
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        this.playerDefaultSpawnPoint = new Position((mapRows - 1) * 3 / 4, (mapCols - 1) / 2);
        this.onSectionInstantiated.subscribe(bindings);
        createNewSection();
    }

    private void createNewSection() {
        this.currentSection = new Section(this);
        this.onSectionInstantiated.invoke(
            new SectionInstantiatedArgs(this.currentSection.getEntityAddedEvent(), this.currentSection.getPlayerAddedEvent())
        );
        this.currentSection.generateEntities();
        this.currentSection.addPlayer(new Player(currentSection, this.playerDefaultSpawnPoint));
    }

    void createNewSection(final Function<Section, Player> player) {
        this.currentSection.entityRemovedEventInvoke();
        this.currentSection = new Section(this);
        this.onSectionInstantiated.invoke(
            new SectionInstantiatedArgs(this.currentSection.getEntityAddedEvent(), this.currentSection.getPlayerAddedEvent())
        );
        this.currentSection.generateEntities();
        this.currentSection.addPlayer(player.apply(currentSection));
    }

    void loadSavedSection() {
        if (saved.isPresent()) {
            this.currentSection.entityRemovedEventInvoke();
            this.currentSection = saved.get();
            this.onSectionInstantiated.invoke(
                new SectionInstantiatedArgs(
                    this.currentSection.getEntityAddedEvent(), this.currentSection.getPlayerAddedEvent()
                )
            );
            this.currentSection.addPlayer(saved.get().getPlayer());
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
    public Optional<Section> getSavedState() {
        return saved;
    }

    @Override
    public void setSavedState() {
        saved = Optional.of(new Section(currentSection));
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
    /**
     * 
     * @return Position where player spawns by default
     */
    Position getPlayerDefaultSpawnPoint() {
        return playerDefaultSpawnPoint;
    }
}
