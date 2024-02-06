package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.State;

public class WorldImpl implements World {

    private State savedState;

    private Section currentSection = new SectionImpl();

    public WorldImpl() {
    }

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
    public void generateSection() {
        currentSection = new SectionImpl();
    }
}
