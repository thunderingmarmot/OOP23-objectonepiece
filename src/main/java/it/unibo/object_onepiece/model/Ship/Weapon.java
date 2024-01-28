package it.unibo.object_onepiece.model.Ship;

import it.unibo.object_onepiece.model.Utils.Position;

public interface Weapon extends ShipComponent {
    public boolean shoot(Position position);

    public int getMaxDamage();
    public int getMinDamage();
    public int getRange();
}