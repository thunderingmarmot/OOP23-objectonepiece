package it.unibo.object_onepiece.view;

import javafx.scene.paint.Color;

class ComponentBar extends ProgressBarDecorator  {
    private static final Color DARK_GREEN = Color.rgb(56, 84, 5);
    private static final Color DARK_RED = Color.rgb(201, 10, 10);

    protected ComponentBar(ProgressBar decorated) {
        super(decorated);
        this.setColor(DARK_RED, DARK_GREEN);
    }   
}
