package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Utils.Position;

public interface PatrolStrategy {
    
    public Position Move( Position playerPosition );
}
