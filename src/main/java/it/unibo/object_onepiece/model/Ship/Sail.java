package it.unibo.object_onepiece.model.Ship;

import it.unibo.object_onepiece.model.Utils.Position;

public interface Sail extends ShipComponent {
    public boolean isInSpeedRange(Position currPosition, Position nextPosition);

    public int getMaxSpeed();
    public int getMinSpeed();
    public int getRotationPower();
}
