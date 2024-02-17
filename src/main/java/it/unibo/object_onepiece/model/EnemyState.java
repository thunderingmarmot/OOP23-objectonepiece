package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.EnemyImpl.States;

abstract class EnemyState {
    /**
     * @return true if the action was executed
     * false if not ( is given that the state has been changed and another attempt will be performed )
     */
    protected abstract Boolean perform();
    /**
     * @return the state it rappresents
     */
    protected abstract States getState();
}
