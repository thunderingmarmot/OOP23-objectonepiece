package it.unibo.object_onepiece.view;

import javafx.scene.paint.Color;

public class ExperienceBar extends ProgressBarDecorator {

    protected ExperienceBar(ProgressBar b) {
        super(b);
        this.setColor(Color.BLUE, Color.BLUE);
    }

    @Override
    public void update(final int progress, final int maxProgress) {
        this.decorated.update(progress, progress);
    }
}
