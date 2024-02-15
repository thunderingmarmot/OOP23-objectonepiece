package it.unibo.object_onepiece.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Optional;
import java.util.function.BiFunction;

import edu.umd.cs.findbugs.annotations.OverrideMustInvoke;
/**
 * Basic progress bar with predifined dimensions and label to view progress in text form.
 */
public final class ProgressBarImpl implements ProgressBar {
    private static final int BAR_WIDTH = 20;
    private static final int BAR_HEIGHT = 100;

    private Color backColor = Color.WHITE;
    private Color frontColor = Color.BLACK;
    private int progress;
    private Optional<Integer> maxProgress = Optional.empty();
    private String name = "";
    private final BiFunction<Integer, Integer, String> labelTextBuild = (h, maxH) -> h + "/" + maxH;

    @Override
    public VBox getContainer() {
        final VBox container = new VBox();
        final StackPane s = new StackPane();
        final Label label = new Label();
        final Rectangle backRectangle = new Rectangle();
        backRectangle.setFill(backColor);
        final Rectangle frontRectangle = new Rectangle();
        frontRectangle.setFill(frontColor);
        s.setPrefHeight(BAR_HEIGHT);
        s.setMaxWidth(BAR_WIDTH);
        backRectangle.widthProperty().bind(s.widthProperty());
        backRectangle.heightProperty().bind(s.heightProperty());
        frontRectangle.widthProperty().bind(s.widthProperty());
        StackPane.setAlignment(frontRectangle, Pos.BOTTOM_CENTER);
        s.getChildren().addAll(backRectangle, frontRectangle);
        container.getChildren().addAll(s);
        if (!name.isEmpty()) {
            container.getChildren().add(new Label(name));
        }
        container.getChildren().add(label);
        container.setAlignment(Pos.CENTER);
        if (maxProgress.isEmpty()) {
            frontRectangle.heightProperty().bind(backRectangle.heightProperty());
            label.setText(Integer.toString(progress));
        } else if (maxProgress.isPresent()) {
            label.setText(labelTextBuild.apply(progress, maxProgress.get()));
            frontRectangle.heightProperty().bind(
                backRectangle.heightProperty().map(e -> (e.doubleValue() * progress) / maxProgress.get())
            );
        }

        return container;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setProgress(final int progress) {
        this.progress = progress;
        maxProgress = Optional.empty();
    }

    @Override
    public void setProgressMaxProgress(final int progress, final int maxProgress) {
        if (maxProgress <= 0) {
            throw new IllegalArgumentException("maxProgress cannot be less or equal to 0");
        }
        this.progress = progress;
        this.maxProgress = Optional.of(maxProgress);
    }

    @Override
    public void setColor(final Color back, final Color front) {
        backColor = back;
        frontColor = front;
    }

}
