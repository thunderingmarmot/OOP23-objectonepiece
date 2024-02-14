package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * An implementation of patrol state of Enemy.
 */
public final class Patrol extends EnemyState {
    private final NavigationSystem compass;
    private final Enemy ship;
    private final Bound bound;
    private final States stato = States.PATROLLING;
    private Position objective;
    /**
     * The constructor of Patrol.
     * @param ship
     * @param compass
     */
    protected Patrol(final Enemy ship, final NavigationSystem compass) {
        this.ship = ship;
        this.compass = compass;
        this.bound = ship.getSection().getBounds();
    }

    @Override
    public Boolean perform() {
        if (objective == null || ship.getPosition().equals(objective)) {
            defineRandomObjective();
        } 

        final var suggestedDir = compass.move(objective, this.ship.getPosition());

        if (!Enemy.ACTION_SUCCESS_CONDITIONS.contains(ship.move(suggestedDir, 1))) {
            ship.changeState(States.AVOIDING);
            return false;
        } 

        checkPlayer();
        return true;
    }

    @Override
    public States getState() {
       return stato;
    }

    private boolean checkPlayer() {
        if (this.ship.getPosition().distanceFrom(playerPos()) <= ship.getTriggerDistance()) {
            this.ship.changeState(States.ATTACKING);
            return true;
        }
        return false;
    }

    private Position playerPos() {
        return this.ship.getSection().getPlayer().getPosition();
    }

    private void defineRandomObjective() {
        do {
            final int x = Utils.getRandom().nextInt(bound.columns());
            final int y = Utils.getRandom().nextInt(bound.rows());

            objective = new Position(x, y);
        } while (!bound.isInside(objective) || this.ship.getSection().getEntityAt(objective).isPresent());
    }
}
