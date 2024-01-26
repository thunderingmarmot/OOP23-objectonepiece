package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public class IslandImpl extends EntityImpl implements Island {

    private final int healthGiven;

    public IslandImpl(Section section, Position position, int healthGiven) {
        super(section, position);
        this.healthGiven = healthGiven;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void heal() {
        this.getSection().getPlayer().heal(healthGiven);
    }

    @Override
    public Runnable getInteraction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInteraction'");
    }
    
}
