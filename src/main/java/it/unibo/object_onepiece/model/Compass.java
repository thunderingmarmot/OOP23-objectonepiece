package it.unibo.object_onepiece.model;
import java.util.Random;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * Compass is the implementation of NavigationSystem.
 */
public final class Compass implements NavigationSystem {
 
    private final Random rand = new Random(); 
    private Position objective;
    private final Bound bound;

    /**
     * The contructor of Compass.
     * @param currentPosition is the position of the ship in which the compass is present
     * @param bound is the limit of the map, used to define random objective
     */
    public Compass(final Position currentPosition, final Bound bound) {
        this.bound = bound;
        defineRandomObjective(currentPosition);
    }

    @Override
    public CardinalDirection move(final Position objectivePosition, final Position currentPosition) {
        return currentPosition.whereTo(objectivePosition);
    }

    public CardinalDirection move(final Position currentPosition){
        if (objectiveReached(objective)){
            defineRandomObjective(currentPosition);
        } 
        return this.move(objective, currentPosition);
    }

    private void defineRandomObjective(final Position currentPosition){
        final int maxDistance = 5;
        do {
            int x = rand.nextInt(maxDistance);
            int y = rand.nextInt(maxDistance);

            objective = currentPosition.translate(new Position(x, y));
        } while ( !bound.isInside(objective) );
    }

    private Boolean objectiveReached(final Position position) {
        if (position.equals(objective)) {
            return true;
        }  
        return false; 
    }
}