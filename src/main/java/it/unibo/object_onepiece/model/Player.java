package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.*;
import it.unibo.object_onepiece.model.ship.Bow;
import it.unibo.object_onepiece.model.ship.Sail;
import it.unibo.object_onepiece.model.ship.Ship;
import it.unibo.object_onepiece.model.ship.Weapon;

//Interface that models the player's ship

public interface Player extends Viewable, Ship {
    public static Player getDefault(Section spawnSection, Position spawnPosition) {
        return new PlayerImpl(spawnSection, spawnPosition, Direction.UP, Weapon.cannon(), Sail.sloop(), Bow.standard(), 0);
    }

    public int getExperience();
    public void addExperience(int experience);
}
