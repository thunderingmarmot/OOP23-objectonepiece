package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.model.World;
import it.unibo.object_onepiece.model.Utils.Position;

public interface Controller {
    enum States{
        SHOOTING,
        MOVING
    }
    public void action(Position position,World World);
    public void initialize(World world);
}
