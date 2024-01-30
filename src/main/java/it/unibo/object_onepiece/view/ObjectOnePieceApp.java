package it.unibo.object_onepiece.view;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

import eu.lestard.grid.GridModel;
import eu.lestard.grid.GridView;
import it.unibo.object_onepiece.model.*;
import it.unibo.object_onepiece.model.Ship.Ship;
import it.unibo.object_onepiece.model.Utils.Direction;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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

    public enum EntityType {
        ENEMY,
        PLAYER,
        BARREL,
        ISLAND,
        WATER;
    }

    private static final Function<Entity, Pair<EntityType, Optional<Direction>>> fun = new Function<>() {

        @Override
        public Pair<EntityType, Optional<Direction>> apply(Entity t) {
            

            return null;
        }
    };

    private static final Map<EntityType, Predicate<Entity>> isInstanceOfEntity = Map.of(
        EntityType.PLAYER, e -> e instanceof Player,
        EntityType.BARREL, e -> e instanceof Barrel,
        EntityType.ISLAND, e -> e instanceof Island
    ); 

    private static final Function<Entity, EntityType> getEntityType = new Function<>() {
        @Override
        public EntityType apply(Entity t) {
            var typeList = Stream.of(EntityType.values()).filter(e -> isInstanceOfEntity.containsKey(e) && isInstanceOfEntity.get(e).test(t)).toList();
            if (typeList.size() != 1) {
                throw new IllegalArgumentException("Could not find an EntityType for passed Entity");
            }

            return typeList.get(0);
        }
    };

    private final GridModel<Pair<EntityType, Optional<Direction>>> gridModel = new GridModel<>();
    private final GridView<Pair<EntityType, Optional<Direction>>> gridView = new GridView<>();
    private final World world = new WorldImpl();

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Object One Piece!");
        gridSetUp();
        world.getCurrentSection().getEntities().forEach(e -> {
            EntityType et = getEntityType.apply(e);
                drawEntity(e.getPosition().row(), e.getPosition().column(), et, Optional.of(Direction.UP));
        });
        BorderPane borderPane = new BorderPane();
        
        Canvas health = new Canvas(100, 100);
        GraphicsContext healthGC = health.getGraphicsContext2D();
        Rectangle healthBar = new Rectangle(20, 100);
        drawHealthBar(healthGC, healthBar);

        Label healthPoints = new Label("100");

        VBox healthContainer = new VBox();
        healthContainer.getChildren().addAll(health, healthPoints);


        borderPane.setCenter(gridView);
        borderPane.setRight(healthContainer);
        Scene scene = new Scene(borderPane, 600, 600);
        scene.getStylesheets().add("/css/ObjectOnePieceApp.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void gridSetUp() {
        gridModel.setDefaultState(new Pair<>(EntityType.WATER, Optional.empty()));
        gridModel.setNumberOfColumns(MAP_COLUMNS);
        gridModel.setNumberOfRows(MAP_ROWS);
        gridView.setGridModel(gridModel);
        gridModel.getCells().forEach(i -> gridView.addColorMapping(new Pair<>(EntityType.WATER, Optional.empty()), DEFAULT_COLOR));
        gridView.cellBorderColorProperty().set(CELL_BORDER_COLOR);

        Stream.of(EntityType.values()).forEach(i -> {
            final String entityName = i.toString().toLowerCase();
            final URL imgPath = getClass().getResource("/img/sprites/" + entityName + "/" + entityName + ".png");
            if (imgPath != null) {
                LinkedList<Optional<Direction>> od = new LinkedList<>();
                Stream.of(Direction.values()).forEach(d -> od.add(Optional.of(d)));
                od.add(Optional.empty());
                od.forEach(j -> {
                    gridView.addNodeMapping(new Pair<>(i, j), cell -> {
                        final Image img = new Image(imgPath.toString());
                        final ImageView entityImage = new ImageView(img);
                        if (cell.getState().getY().isPresent()) {
                            var direction = cell.getState().getY().get();
                            entityImage.setRotate(RIGHT_ANGLE * direction.ordinal());
                        }
                        entityImage.setPreserveRatio(true);
                        entityImage.fitWidthProperty().bind(gridView.cellSizeProperty());
                        entityImage.fitHeightProperty().bind(gridView.cellSizeProperty());
                        return entityImage;
                    });
                    gridView.addColorMapping(new Pair<>(i, j), DEFAULT_COLOR);
                });
            }
        });
    }

    private void drawEntity(int row, int col, EntityType e, Optional<Direction> d) {
        gridModel.getCell(col, row).changeState(new Pair<>(e, d));
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
