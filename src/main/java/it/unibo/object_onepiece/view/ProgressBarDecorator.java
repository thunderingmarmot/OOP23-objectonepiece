package it.unibo.object_onepiece.view;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

abstract class ProgressBarDecorator implements ProgressBar {
    private final ProgressBar decorated;

    protected ProgressBar getDecorated() {
        return decorated;
    }

    protected ProgressBarDecorator(final ProgressBar decorated) {
        this.decorated = decorated;
    }

    @Override
    public VBox getContainer() {
        return this.decorated.getContainer();
    }

    @Override
    public void setProgress(final int progress) {
        this.decorated.setProgress(progress);
    }

    @Override
    public void setProgressMaxProgress(final int progress, final int maxProgress) {
        this.decorated.setProgressMaxProgress(progress, maxProgress);
    }

    @Override
    public void setColor(final Color back, final Color front) {
        this.decorated.setColor(back, front);
    }
}
