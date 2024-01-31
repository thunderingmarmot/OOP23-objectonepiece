package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Ship.Ship;

/**
 * The barrels are collectable entities for the user
 */
public interface Barrel extends Viewable, Collidable {
    public void take(Ship ship);
}
