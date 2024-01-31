package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Ship.Bow;
import it.unibo.object_onepiece.model.Ship.Sail;
import it.unibo.object_onepiece.model.Ship.Ship;
import it.unibo.object_onepiece.model.Ship.Weapon;
import it.unibo.object_onepiece.model.Utils.*;

//Interface that models the player's ship

public interface Player extends Viewable, Ship {
    public static Player getDefault(Section spawnSection, Position spawnPosition) {
        return new PlayerImpl(spawnSection, spawnPosition, Direction.UP, Weapon.cannon(), Sail.sloop(), Bow.standard(), 0);
    }

    public int getExperience();
    public void addExperience(int experience);
}
