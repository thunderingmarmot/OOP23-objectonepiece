package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.State;

public class WorldImpl implements World {

    private State savedState;

    private Section currentSection;

    public WorldImpl() {
        this.currentSection = generateSection();
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentSection'");
    }

    @Override
    public Section generateSection() {
        return new SectionImpl();
    }
}
