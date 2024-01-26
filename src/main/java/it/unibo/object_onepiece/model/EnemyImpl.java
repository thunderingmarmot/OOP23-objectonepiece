package it.unibo.object_onepiece.model;

import java.util.Random;

import it.unibo.object_onepiece.model.Utils.*;

public class EnemyImpl extends ShipImpl implements Enemy {
    private PatrolStrategy patrolStrategy = new PatrolImpl();
    private State state;
    private final int VIEWDISTANCE = 3;
    private final int ATTACKDISTANCE = 2;

    public EnemyImpl(Section section, Position position, Direction direction, int health) {
        super(section, position, direction, health);
    }

    @Override
    public void goNext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'goNext'");
    }

    @Override
    public State getCurrentState() {
        return state;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENEMY;
    }

    private Position generateMovement(){
        updateState();
        switch (state) {
            case PATROLLING:
                return patrol();

            case FOLLOWING:
                return follow();

            default:
                return null;
        }
    }

    private void updateState(){
        Position playerPosition = this.section.getPlayer().getPosition();
        
        if(this.position.distanceFrom(playerPosition) > VIEWDISTANCE){
            this.state = State.PATROLLING;
        } else{
            this.state = State.FOLLOWING;
        }

    }

    private Position patrol(){
        return patrolStrategy.Move(this.position);
    }

    private Position follow(){

    }

    private class PatrolImpl implements PatrolStrategy {
        final int maxDistance = 5;
        final int minDistance = 2;
        final Random rand = new Random(); 

        Position Objective;

        @Override
        public Position Move(Position playerPosition) {
            if(objectiveReached(playerPosition)){
                defineObjective();
            } 

            return Objective;
        }

        private void defineObjective(){
            int x = minDistance + rand.nextInt(maxDistance - minDistance);
            int y = minDistance + rand.nextInt(maxDistance - minDistance);

            Objective = Objective.traslate(new Position(x, y));

        }

        private Boolean objectiveReached(Position position){
            if(position.equals(Objective)){
                return true;
            }else{ return false; } 
        }

    
        
    } 
    
}
