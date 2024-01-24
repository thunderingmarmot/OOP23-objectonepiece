package it.unibo.object_onepiece.view;

import java.util.Optional;

import javafx.scene.paint.Color;

public enum EntityView {
    WATER(Optional.of(Color.rgb(2, 127, 222))),
    PLAYER(Optional.empty()),
    ENEMY(Optional.empty()),
    BARREL(Optional.empty()),
    ISLAND(Optional.empty());

    public final Optional<Color> color;

    private EntityView(Optional<Color> c) {
        this.color = c;
    }
}
