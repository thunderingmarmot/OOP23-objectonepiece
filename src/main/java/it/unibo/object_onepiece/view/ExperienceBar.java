package it.unibo.object_onepiece.view;

import javafx.scene.paint.Color;

class ExperienceBar extends ProgressBarDecorator {
    protected ExperienceBar(final ProgressBar b) {
        super(b);
        this.setColor(Color.BLUE, Color.BLUE);
    }
}
