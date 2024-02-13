package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Enemy.States;
import it.unibo.object_onepiece.model.Enemy.EnemyState;
import it.unibo.object_onepiece.model.Utils.Bound;
import it.unibo.object_onepiece.model.Utils.Position;

public final class Patrol extends EnemyState {
    private final NavigationSystem compass;
    private final Enemy ship;
    private final Bound bound;
    private final States stato = States.PATROLLING;
    private Position objective = null;

    protected Patrol(final Enemy ship, final NavigationSystem compass) {
        this.ship = ship;
        this.compass = compass;
        this.bound = ship.getSection().getBounds();
    }

    @Override
    public Boolean perform() {
        if (objective == null || ship.getPosition().equals(objective)) {
            defineRandomObjective(ship.getPosition());
        } 

        var suggestedDir = compass.move(objective, this.ship.getPosition());

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

    private void defineRandomObjective(final Position currentPosition) {
        do {
            int x = Utils.getRandom().nextInt(bound.columns());
            int y = Utils.getRandom().nextInt(bound.rows());

            objective = new Position(x, y);
        } while (!bound.isInside(objective));

        System.err.println("random objective:" + objective.toString());
    }
}
