package it.unibo.object_onepiece.view;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import eu.lestard.grid.GridModel;
import eu.lestard.grid.GridView;
import it.unibo.object_onepiece.controller.Controller;
import it.unibo.object_onepiece.controller.ControllerImpl;
import it.unibo.object_onepiece.model.World;
import it.unibo.object_onepiece.model.WorldImpl;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.view.Sound.SoundTypes;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
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
    private static final int HP_BARS_COUNT = 4;
    private static final float DEFAULT_AMBIENCE_SOUND_VOLUME = -30;

    private static final Function<String, String> PATH_FUNC = t -> "/img/sprites/" + t + "/" + t + ".png";
    private final String styleSheet = "/css/ObjectOnePieceApp.css";

    private enum State {
        WATER;
    }

    private final GridModel<State> gridModel = new GridModel<>();
    private final GridView<State> gridView = new GridView<>();
    private Controller controller = new ControllerImpl();
    private final ProgressBar[] healthBars = new HealthBar[4];
    private final Sound sound = new Sound();
    final Label experienceText = new Label();
    private World world;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Object One Piece!");
        gridSetUp();
        final VBox barsContainer = new VBox();
        final BorderPane borderPane = new BorderPane();

        sound.playAmbienceSound(DEFAULT_AMBIENCE_SOUND_VOLUME);

        final Label pirateInfo = new Label("Pirate info!");
        pirateInfo.setAlignment(Pos.CENTER);

        final BorderPane rightPane = new BorderPane();
        rightPane.setTop(pirateInfo);
        rightPane.setCenter(barsContainer);

        borderPane.setCenter(gridView);
        borderPane.setRight(rightPane);

        final Scene scene = new Scene(borderPane, 600, 600);
        scene.getStylesheets().add(styleSheet);
        primaryStage.setScene(scene);
        primaryStage.show();

        for(int i = 0; i < HP_BARS_COUNT; i++) {
            healthBars[i] = new HealthBar(new ProgressBarImpl());
            barsContainer.getChildren().add(healthBars[i].getContainer());
        }

        experienceText.setAlignment(Pos.CENTER);

        ImageView heal = new ImageView(new Image("/img/ui/heal.png"));
        heal.setPreserveRatio(true);
        heal.setFitWidth(barsContainer.getWidth());

        final Button useXp = new Button();
        useXp.setGraphic(heal);

        final VBox xpContainer = new VBox();
        xpContainer.setAlignment(Pos.CENTER);
        xpContainer.getChildren().addAll(useXp, experienceText);

        barsContainer.getChildren().add(xpContainer);
        barsContainer.setAlignment(Pos.CENTER);

        world = new WorldImpl(MAP_ROWS, MAP_COLUMNS, (e1) -> {
            e1.onEntityAdded().subscribe((e2) -> {
                e2.onEntityCreated().subscribe((e3) -> createEntity(e3.name(), e3.spawnPosition(), e3.spawnDirection()));
                e2.onEntityUpdated().subscribe((e3) -> updateEntity(e3.name(), e3.oldPosition(), e3.newPosition(), e3.newDirection()));
                e2.onEntityRemoved().subscribe((e3) -> removeEntity(e3.lastPosition()));
            });
            e1.onPlayerAdded().subscribe((e2) -> {
                e2.onPlayerUpdated().subscribe((e3) -> drawPlayerInfo(e3.healthList(), e3.maxHealthList(), e3.experience()));
            });
        });

        useXp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.pressGameButton(Controller.Buttons.FIX, world);
            }
        });
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
                    Position p = new Position(c.getRow(), c.getColumn());
                    if (event.getButton() == MouseButton.SECONDARY) {
                        controller.action(p, world, Controller.States.SHOOTING);
                    } else {
                        controller.action(p, world, Controller.States.MOVING);
                    }
                }
            });
            /*gridView.getCellPane(c).addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    Border b = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        BorderWidths.DEFAULT));
                    Pane p = new Pane();
                    p.setBorder(b);
                    gridView.getCellPane(c).getChildren().add(p);
                }
            });
            gridView.getCellPane(c).addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    gridView.getCellPane(c).getChildren().stream()
                        .filter(n -> n instanceof Pane).map(n -> (Pane) n).forEach(n -> n.setBorder(null));
                }
            });*/
        });
        gridView.cellBorderColorProperty().set(CELL_BORDER_COLOR);
    }


    
    private void createEntity(String name, Position spawnPosition, CardinalDirection spawnDirection) {
        drawImage(name, spawnPosition.row(), spawnPosition.column(), Optional.of(spawnDirection));
    }


    private void updateEntity(String name, Position oldPosition, Position newPosition, CardinalDirection newDirection) {
        removeEntity(oldPosition);
        drawImage(name, newPosition.row(), newPosition.column(), Optional.of(newDirection));
    }

    private void removeEntity(Position lastPosition) {
        final int col = lastPosition.column();
        final int row = lastPosition.row();

        if (gridView.getCellPane(gridModel.getCell(col, row)).getChildren().size() == 0) {
            throw new IllegalArgumentException("Trying to delete cell view where there isn't anything");
        } else {
            gridView.getCellPane(gridModel.getCell(col, row)).getChildren().clear();
        }
    }

    private void drawImage(final String entityName, final int row, final int col, final Optional<CardinalDirection> d) {
        try {
            final URL imgPath = getClass().getResource(PATH_FUNC.apply(entityName.toLowerCase()));
            final Image img = new Image(imgPath.toExternalForm());
            final ImageView entityImage = new ImageView(img);
            if (d.isPresent()) {
                entityImage.setRotate(RIGHT_ANGLE * d.get().ordinal());
            }
            entityImage.setPreserveRatio(true);
            entityImage.fitWidthProperty().bind(gridView.cellSizeProperty());
            entityImage.fitHeightProperty().bind(gridView.cellSizeProperty());
            if (gridView.getCellPane(gridModel.getCell(col, row)).getChildren().size() > 0) {
                gridView.getCellPane(gridModel.getCell(col, row)).getChildren().stream().forEach(System.out::println);
                throw new IllegalStateException("Cell where entity should be drawn already has another entity");
            }
            gridView.getCellPane(gridModel.getCell(col, row)).getChildren().add(entityImage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            gridView.getCellPane(gridModel.getCell(col, row)).getChildren().add(new Label(entityName));
        }
    }

    private void drawPlayerInfo(List<Integer> healthList, List<Integer> maxHealthList, int experience) {
        if (Stream.of(healthList.size(), maxHealthList.size()).anyMatch(s -> s > HP_BARS_COUNT)) {
            throw new IllegalArgumentException("Model has more healthbars than view can represent");
        }
        
        for (int i = 0; i < HP_BARS_COUNT; i++) {
            healthBars[i].update(healthList.get(i), maxHealthList.get(i));
        }
        
        experienceText.setText(Integer.toString(experience));
    }
    /**
     * Program's entry point.
     * 
     * @param args
     */
    public static void run(final String... args) {
        launch(args);
    }

    // Defining the main methods directly within JavaFXApp may be problematic:
    // public static void main(final String[] args) {
    // run();
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
         * 
         * @param args
         */
        public static void main(final String... args) {
            Application.launch(ObjectOnePieceApp.class, args);
            /*
             * The following line raises: Error: class it.unibo.samplejavafx.JavaFXApp$Main
             * is not a subclass of javafx.application.Application
             * Because if you do not provide the Application subclass to launch() it will
             * consider the enclosing class)
             */
            // JavaFXApp.launch(args);
            // Whereas the following would do just fine:
            // JavaFXApp.run(args)
        }
    }
}
