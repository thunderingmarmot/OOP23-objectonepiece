package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public interface Weapon {
    public boolean shoot(Position position);

    public Ship getShip();
    public int getMaxDamage();
    public int getMinDamage();
    public int getRange();
}
