package it.unibo.object_onepiece.view;

import java.util.Optional;

import javax.swing.text.html.Option;

import it.unibo.object_onepiece.view.ObjectOnePieceApp.Entity;
import javafx.scene.paint.Color;

public class EntityView {

    private static final Color DEFAULT_COLOR = Color.rgb(2, 127, 222);

    private Color color = DEFAULT_COLOR;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    private EntityType type = EntityType.WATER;

    private static enum EntityType {
        BARREL,
        ISLAND,
        ENEMY,
        PLAYER,
        WATER
    }

    public EntityView() {

    }

    public EntityView(final Color c) {
        this.color = c;
    }
}
