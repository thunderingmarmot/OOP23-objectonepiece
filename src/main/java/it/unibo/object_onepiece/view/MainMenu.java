package it.unibo.object_onepiece.view;

import java.util.stream.DoubleStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class MainMenu  {



    protected final String styleSheet = "css/MainMenu.css";

    private final SceneController sc;

    public MainMenu(SceneController sc) {
        this.sc = sc;
    }

    public Pane setUp() {
        GridPane grid = new GridPane();
        grid.getRowConstraints().addAll(DoubleStream.of(10, 20, 20, 50).mapToObj(height -> {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(height);
            rc.setFillHeight(true);
            return rc;
        }).toArray(RowConstraints[]::new));
        grid.getColumnConstraints().add(new ColumnConstraints(50));

        Label gameName = new Label("Object One Piece!");
        gameName.setFont(new Font(20));
        grid.add(gameName, 2, 1);

        Button start = new Button("Start!");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sc.switchToGame();
            }
        });
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sc.closeScene();
            }
        });

        VBox labelContainer = new VBox(20);
        labelContainer.getChildren().addAll(start, quit);
        labelContainer.getChildren().forEach(i -> VBox.setMargin(i, new Insets(0, 0, 0, 50)));
        grid.add(labelContainer, 2, 3);

    
        
        return grid;
    }
}
