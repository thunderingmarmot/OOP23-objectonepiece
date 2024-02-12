package it.unibo.object_onepiece.view;

import javafx.scene.paint.Color;

class HealthBar extends ProgressBarDecorator {
    protected HealthBar(final ProgressBar b) {
        super(b);
        this.setColor(Color.RED, Color.GREEN);
    }
}
