package it.unibo.object_onepiece.model;

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
    private Section currentSection = new SectionImpl();

    @Override
    public State getSavedState() {
        return savedState;
    }

    @Override
    public void setSavedState() {
        Player player = getCurrentSection().getPlayer();
        savedState = new State(getCurrentSection(), player.getPosition(), player.getExperience());
    }

    @Override
    public Section getCurrentSection() {
        return currentSection;
    }

    @Override
    public void instantiateSection() {
        currentSection = new SectionImpl();
    }
}
