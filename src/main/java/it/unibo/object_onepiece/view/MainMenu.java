package it.unibo.object_onepiece.view;

import java.awt.Color;
import java.util.stream.DoubleStream;

import javax.swing.SpringLayout.Constraints;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Sample JavaFX application.
 */
public final class MainMenu extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Object One Piece!");

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
        Button quit = new Button("Quit");

        VBox labelContainer = new VBox(20);
        labelContainer.getChildren().addAll(start, quit);
        labelContainer.getChildren().forEach(i -> VBox.setMargin(i, new Insets(0, 0, 0, 50)));

        grid.add(labelContainer, 2, 3);

        Scene scene = new Scene(grid, 600, 600);
        scene.getStylesheets().add("css/MainMenu.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Program's entry point.
     * @param args
     */
    public static void run(final String... args) {
        launch(args);
    }

    // Defining the main methods directly within JavaFXApp may be problematic:
    // public static void main(final String[] args) {
    //        run();
    // }

    /**
     * Entry point's class.
     */
    public static final class Main {
        private Main() {
            // the constructor will never be called directly.
        }

        /**
         * Program's entry point.
         * @param args
         */
        public static void main(final String... args) {
            Application.launch(MainMenu.class, args);
            /* 
            The following line raises: Error: class it.unibo.samplejavafx.JavaFXApp$Main 
            is not a subclass of javafx.application.Application
            Because if you do not provide the Application subclass to launch() it will consider the enclosing class)
            */
            // JavaFXApp.launch(args);
            // Whereas the following would do just fine:
            // JavaFXApp.run(args)
        }
    }
}
