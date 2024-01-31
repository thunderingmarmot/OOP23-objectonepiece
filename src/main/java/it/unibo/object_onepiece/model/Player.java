package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Ship.Ship;

//Interface that models the player's ship

public interface Player extends Viewable, Ship {
    public int getExperience();
    public void addExperience(int experience);
}
