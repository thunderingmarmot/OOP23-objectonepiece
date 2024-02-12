package it.unibo.object_onepiece.view;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class ProgressBarDecorator implements ProgressBar {

    protected final ProgressBar decorated;

    protected ProgressBarDecorator(final ProgressBar decorated) {
        this.decorated = decorated;
    }

    @Override
    public VBox getContainer() {
        return this.decorated.getContainer();
    }

    @Override
    public void update(int progress) {
        this.decorated.update(progress);
    }

    @Override
    public void update(int progress, int maxProgress) {
        this.decorated.update(progress, maxProgress);
    }

    @Override
    public void setColor(Color back, Color front) {
        this.decorated.setColor(back, front);
    }
    
}
