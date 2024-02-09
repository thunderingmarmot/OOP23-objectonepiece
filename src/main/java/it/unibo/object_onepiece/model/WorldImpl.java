package it.unibo.object_onepiece.model;

import java.util.function.Consumer;

import it.unibo.object_onepiece.model.Utils.State;
import it.unibo.object_onepiece.model.events.Event;
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
    private final Event<Argument<Section>> onSectionInstantiated = new Event<>();

    public WorldImpl(final Consumer<Argument<Section>> listener) {
        onSectionInstantiated.subscribe(listener);
        createNewSection();
    }

    void createNewSection() {
        currentSection = new Section(this);
        onSectionInstantiated.invoke(new Argument<>(currentSection));
        currentSection.generateEntities();
    }
    
    public Event<Argument<Section>> getSectionInstantiatedEvent() {
        return onSectionInstantiated;
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

    @Override
    public Section getCurrentSection() {
        return currentSection;
    }

    @Override
    public void instantiateSection() {
        currentSection = new Section(this);
    }
}
