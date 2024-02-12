package it.unibo.object_onepiece.view;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public interface ProgressBar {
    VBox getContainer();
    void update(int progress, int maxProgress);
    void setColor(Color back, Color front);
}
