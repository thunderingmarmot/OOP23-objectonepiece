package it.unibo.object_onepiece.model.Ship;

import it.unibo.object_onepiece.model.Utils.Position;

public interface Weapon extends ShipComponent {
    public enum ShootDetails {
        SHOOTED_SUCCESSFULLY,
        OUT_OF_SHOOTING_RANGE,
        WEAPON_BROKEN
    };

    public ShootDetails shoot(Position position);

    public int getMaxDamage();
    public int getMinDamage();
    public int getRange();
}
