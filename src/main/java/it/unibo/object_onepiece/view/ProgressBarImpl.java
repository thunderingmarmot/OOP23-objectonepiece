package it.unibo.object_onepiece.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.function.BiFunction;

public class ProgressBarImpl implements ProgressBar {
    private final static int BAR_WIDTH = 20;
    private final static int BAR_HEIGHT = 100;

    private final VBox container = new VBox();
    private final StackPane s = new StackPane();
    private final Label label = new Label();
    private final Rectangle backRectangle = new Rectangle();
    private final Rectangle frontRectangle = new Rectangle();
    private final BiFunction<Integer, Integer, String> labelTextBuild = (h, maxH) -> h + "/" + maxH; 


    public ProgressBarImpl() {
        configPane();
    }

    private void configPane() {
        s.setPrefHeight(BAR_HEIGHT);
        s.setMaxWidth(BAR_WIDTH);
        backRectangle.widthProperty().bind(s.widthProperty());
        backRectangle.heightProperty().bind(s.heightProperty());
        frontRectangle.widthProperty().bind(s.widthProperty());
        StackPane.setAlignment(frontRectangle, Pos.BOTTOM_CENTER);
        s.getChildren().addAll(backRectangle, frontRectangle);
        this.label.setAlignment(Pos.CENTER);
        container.getChildren().addAll(s, label);
    }

    @Override
    public VBox getContainer() {
        return container;
    }

    @Override
    public void update(final int progress) {
        frontRectangle.setHeight(backRectangle.getHeight());
        label.setText(Integer.toString(progress));
    }

    @Override
    public void update(final int progress, final int maxProgress) {
        if (maxProgress <= 0) {
            throw new IllegalArgumentException("maxProgress cannot be less or equal to 0");
        }
        label.setText(labelTextBuild.apply(progress, maxProgress));
        frontRectangle.setHeight((backRectangle.getHeight() * progress) / maxProgress);
    }

    @Override
    public void setColor(final Color back, final Color front) {
        backRectangle.setFill(back);
        frontRectangle.setFill(front);
    }
    
}
