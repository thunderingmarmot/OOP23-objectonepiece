package it.unibo.object_onepiece.model;
import java.util.Optional;

import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;

public record GuiEntity(Position position, Optional<Direction> direction, Entity.Type type) {
    
}
