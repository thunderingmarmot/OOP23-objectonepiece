package it.unibo.object_onepiece;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Sample JavaFX application.
 */
public final class ObjectOnePieceApp extends Application {

    private static final int MAP_ROWS = 10;
    private static final int MAP_COLUMNS = 10;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Object One Piece!");

        BorderPane borderPane = new BorderPane();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        
        Canvas health = new Canvas(100, 100);
        GraphicsContext healthGC = health.getGraphicsContext2D();
        Rectangle healthBar = new Rectangle(20, 100);
        drawHealthBar(healthGC, healthBar);

        Label healthPoints = new Label("100");

        VBox healthContainer = new VBox();
        healthContainer.getChildren().addAll(health, healthPoints);

        Button buttons[] = new Button[MAP_ROWS * MAP_COLUMNS];
        for (int i = 0; i < MAP_ROWS; i++) {
            for (int j = 0; j < MAP_COLUMNS; j++) {
                buttons[i] = new Button(i + ", " + j);
                buttons[i].setPadding(new Insets(20));
                gridPane.add(buttons[i], i, j);
            }
        }

        borderPane.setCenter(gridPane);
        borderPane.setRight(healthContainer);
        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawHealthBar(GraphicsContext gc,Rectangle rect) {
        gc.setFill(Color.GREEN);
        gc.fillRect(rect.getX(),      
               rect.getY(), 
               rect.getWidth(), 
               rect.getHeight());
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
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
            Application.launch(ObjectOnePieceApp.class, args);
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
