package it.unibo.object_onepiece.view;

import javafx.scene.paint.Color;

public class HealthBar extends ProgressBarDecorator {

    protected HealthBar(ProgressBar b) {
        super(b);
        this.setColor(Color.RED, Color.GREEN);
    }
}
