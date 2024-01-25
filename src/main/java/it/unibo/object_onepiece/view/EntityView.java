package it.unibo.object_onepiece.view;

import java.util.Optional;

import javafx.scene.paint.Color;

public class EntityView {

    private Optional<Color> color;
    private static enum Type {
        BARREL,
        ISLAND,
        ENEMY,
        PLAYER,
        WATER
    }


    public EntityView(final Optional<Color> c) {
        this.color = c;
    }
}
