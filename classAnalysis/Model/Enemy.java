package Model;

// Interface that models the enemy's ship
public interface Enemy extends Ship {
    public enum State {
        PATROLLING,
        FOLLOWING
    } 

    public void goNext();
    public State getCurrentState();
}
