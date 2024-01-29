package it.unibo.object_onepiece.model.Enemy;
import it.unibo.object_onepiece.model.Enemy.Enemy.States;

public interface EnemyState {
    public void perform();
    public States getState();
}
