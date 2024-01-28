package it.unibo.object_onepiece.view;

import java.util.Optional;

import javax.swing.text.html.Option;

import it.unibo.object_onepiece.view.ObjectOnePieceApp.Entity;
import javafx.scene.paint.Color;

public class EntityView {

    private static final Color DEFAULT_COLOR = Color.rgb(2, 127, 222);

    private Color color = DEFAULT_COLOR;

    public int direction = 0;

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

    public static enum EntityType {
        BARREL,
        ISLAND,
        ENEMY,
        PLAYER,
        WATER;
    }

    public EntityView() {
    }

    public EntityView(final EntityType type) {
        this.type = type;
        this.color = DEFAULT_COLOR;
        this.direction = 0;
    }

    public EntityView(final EntityType type, final int direction) {
        this.type = type;
        this.color = DEFAULT_COLOR;
        this.direction = direction;
    }

    public EntityView(final Color c) {
        this.color = c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final EntityView other = (EntityView) obj;
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }
}
