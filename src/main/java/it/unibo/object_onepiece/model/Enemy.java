package it.unibo.object_onepiece.model;

/**
 * The enemy interface models a ship controlled by the CPU
 */
public interface Enemy extends Ship {
    public enum State {
        PATROLLING,
        FOLLOWING
    } 

    public void goNext();
    public State getCurrentState();
}
