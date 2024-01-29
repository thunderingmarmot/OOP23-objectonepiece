package it.unibo.object_onepiece.model.Enemy;

import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;
/**
 * The navigation system provides a direction to follow
 * to reach the position of the objective,
 * if no objective position is specified, it will generate one
 * within the limits
  */
public interface NavigationSystem {
    /**
     * 
     * @param objectivePosition The position of the objective that we 
     * want to reach
     * @param currentPosition The position of the ship
     * @return The direction to reach the objective
     *
     */
    public Direction move(Position objectivePosition,Position currentPosition);
    public Direction move(Position currentPosition);
}
