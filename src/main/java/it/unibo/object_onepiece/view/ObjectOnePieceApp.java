package it.unibo.object_onepiece.view;

import java.net.URL;
import java.util.Optional;
import java.util.function.Function;

import eu.lestard.grid.GridModel;
import eu.lestard.grid.GridView;
import it.unibo.object_onepiece.controller.Controller;
import it.unibo.object_onepiece.controller.ControllerImpl;
import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.World;
import it.unibo.object_onepiece.model.WorldImpl;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Sample JavaFX application.
 */
public final class ObjectOnePieceApp extends Application {

    private static final int MAP_ROWS = 10;
    private static final int MAP_COLUMNS = 10;

    private static final Color CELL_BORDER_COLOR = Color.rgb(66, 138, 245);
    private static final Color DEFAULT_COLOR = Color.rgb(2, 127, 222);
    private static final int RIGHT_ANGLE = 90;

    private static final Function<String, String> PATH_FUNC = new Function<String, String>() {
        @Override
        public String apply(final String t) {
            return "/img/sprites/" + t + "/" + t + ".png";
        }
    };

    private enum State {
        WATER;
    }

    private final GridModel<State> gridModel = new GridModel<>();
    private final GridView<State> gridView = new GridView<>();
    private Controller controller = new ControllerImpl();
    private final World world = new WorldImpl();

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Object One Piece!");
        gridSetUp();
        world.getCurrentSection();
        world.getCurrentSection().getEntityCreatedEvent().subscribe(e -> drawEntity(e.arg1(), e.arg2(), e.arg3()));
        world.getCurrentSection().generateEntities();
        BorderPane borderPane = new BorderPane();

        Label pirateInfo = new Label("Pirate info!");
        pirateInfo.setAlignment(Pos.CENTER);

        VBox healthContainers[] = new VBox[3];
        for (int i = 0; i < 3; i++) {
            healthContainers[i] = new VBox();
            StackPane healthPane = new StackPane();
            healthPane.setPrefHeight(100);
            healthPane.setMaxWidth(20);
            Rectangle redBar = new Rectangle();
            redBar.setFill(Color.RED);
            redBar.widthProperty().bind(healthPane.widthProperty());
            redBar.heightProperty().bind(healthPane.heightProperty());
            Rectangle healthBar = new Rectangle();
            healthBar.widthProperty().bind(healthPane.widthProperty());
            healthBar.setFill(Color.GREEN);
            healthPane.getChildren().addAll(redBar, healthBar);
            StackPane.setAlignment(healthBar, Pos.BOTTOM_CENTER);
            Label healthPoints = new Label("100");
            healthPoints.setAlignment(Pos.CENTER);
            healthContainers[i].getChildren().addAll(healthPane, healthPoints);
        }

        VBox healthDiv = new VBox();
        
        for (VBox vBox : healthContainers) {
            healthDiv.getChildren().add(vBox);
        }


        BorderPane rightPane = new BorderPane();
        rightPane.setTop(pirateInfo);
        rightPane.setCenter(healthDiv);

        borderPane.setCenter(gridView);
        borderPane.setRight(rightPane);
        Scene scene = new Scene(borderPane, 600, 600);
        scene.getStylesheets().add("/css/ObjectOnePieceApp.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void gridSetUp() {
        gridModel.setDefaultState(State.WATER);
        gridModel.setNumberOfColumns(MAP_COLUMNS);
        gridModel.setNumberOfRows(MAP_ROWS);
        gridView.setGridModel(gridModel);
        gridModel.getCells().forEach(c -> {
            gridView.addColorMapping(State.WATER, DEFAULT_COLOR);
            c.setOnClick(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    controller.action(new Position(c.getRow(), c.getColumn()), world);
                }
            });
        });
        gridView.cellBorderColorProperty().set(CELL_BORDER_COLOR);
    }

    private void drawEntity(Class<? extends Entity> c, Position p, Optional<CardinalDirection> d) {
        var interfaces = c.getInterfaces();
        if (interfaces.length == 0) {
            throw new IllegalStateException("Entity doesnt have interfaces and can't get calculate entityName from it");
        }
        final String entityName = interfaces[0].getSimpleName();
        System.out.println(entityName);
        final int col = p.column();
        final int row = p.row();

        final URL imgPath = getClass().getResource(PATH_FUNC.apply(entityName));
        try {
            final Image img = new Image(imgPath.toString());
            final ImageView entityImage = new ImageView(img);
            if (d.isPresent()) {
                entityImage.setRotate(RIGHT_ANGLE * d.get().ordinal());
            } 
            entityImage.setPreserveRatio(true);
            entityImage.fitWidthProperty().bind(gridView.cellSizeProperty());
            entityImage.fitHeightProperty().bind(gridView.cellSizeProperty());
            if (gridView.getCellPane(gridModel.getCell(col, row)).getChildren().size() > 0) {
                throw new IllegalStateException("Cell where entity should be drawn already has another entity");
            }
            gridView.getCellPane(gridModel.getCell(col, row)).getChildren().add(entityImage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            gridView.getCellPane(gridModel.getCell(col, row)).getChildren().add(new Label(entityName));
        }
    }

    private void drawEntity(Position oldPos, Entity e) {
        var oldCell = gridView.getCellPane(gridModel.getCell(oldPos.column(), oldPos.row()));
        if (oldCell.getChildren().size() < 1) {
            throw new IllegalStateException("Old cell didnt have any images but still removed them");
        }
        oldCell.getChildren().clear();
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
