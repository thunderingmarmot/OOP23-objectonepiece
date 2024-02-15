package it.unibo.object_onepiece.view;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import eu.lestard.grid.GridModel;
import eu.lestard.grid.GridView;
import it.unibo.object_onepiece.controller.Controller;
import it.unibo.object_onepiece.controller.ControllerImpl;
import it.unibo.object_onepiece.model.World;
import it.unibo.object_onepiece.model.WorldImpl;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private static final String STYLE_SHEET = "/css/ObjectOnePieceApp.css";
    private static final Function<String, String> PATH_FUNC = t -> "/img/sprites/" + t + "/" + t + ".png";

    private enum State {
        WATER;
    }
    private final GridModel<State> gridModel = new GridModel<>();
    private final GridView<State> gridView = new GridView<>();
    private final Controller controller = new ControllerImpl();
    private final ProgressBar[] healthBars = new ProgressBar[4];
    private final Sound sound = new Sound();
    private final Label experienceText = new Label();
    private World world;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Object One Piece!");
        gridSetUp();
        final VBox barsContainer = new VBox();
        final BorderPane borderPane = new BorderPane();

        sound.playAmbienceSound();
        sound.setVolume(sound.getAmbienceClip(), DEFAULT_AMBIENCE_SOUND_VOLUME);

        final Label pirateInfo = new Label("Player info");
        pirateInfo.setAlignment(Pos.CENTER);

        final BorderPane rightPane = new BorderPane();
        rightPane.setTop(pirateInfo);
        rightPane.setCenter(barsContainer);

        borderPane.setCenter(gridView);
        borderPane.setRight(rightPane);

        for (int i = 0; i < HP_BARS_COUNT; i++) {
            healthBars[i] = new HealthBar(new ProgressBarImpl());
            barsContainer.getChildren().add(healthBars[i].getContainer());
        }

        final Scene scene = new Scene(borderPane, 600, 600);
        scene.getStylesheets().add(STYLE_SHEET);
        primaryStage.setScene(scene);
        primaryStage.show();

        experienceText.setAlignment(Pos.CENTER);

        final ImageView heal = new ImageView(new Image("/img/ui/heal.png"));
        heal.setPreserveRatio(true);
        heal.setFitWidth(barsContainer.getWidth());

        final ImageView audio = new ImageView(new Image("/img/ui/audio.png"));
        audio.setPreserveRatio(true);
        audio.setFitWidth(barsContainer.getWidth());

        final Button useXp = new Button();
        useXp.setGraphic(heal);

        final Button pauseAmbienceSound = new Button();
        pauseAmbienceSound.setGraphic(audio);

        final VBox xpContainer = new VBox();
        xpContainer.setAlignment(Pos.CENTER);
        xpContainer.getChildren().addAll(useXp, experienceText);

        final VBox volumeContainer = new VBox();
        volumeContainer.setAlignment(Pos.CENTER);
        volumeContainer.getChildren().add(pauseAmbienceSound);

        barsContainer.getChildren().add(xpContainer);
        barsContainer.getChildren().add(volumeContainer);
        barsContainer.setAlignment(Pos.CENTER);

        world = new WorldImpl(MAP_ROWS, MAP_COLUMNS, (e1) -> {
            e1.onEntityAdded().subscribe((e2) -> {
                e2.onEntityCreated()
                    .subscribe((e3) -> createEntity(e3.name(), e3.spawnPosition(), e3.spawnDirection()));
                e2.onEntityUpdated()
                    .subscribe((e3) -> updateEntity(e3.name(), e3.oldPosition(), e3.newPosition(), e3.newDirection()));
                e2.onEntityRemoved()
                    .subscribe((e3) -> removeEntity(e3.lastPosition()));
            });
            e1.onPlayerAdded().subscribe((e2) -> {
                e2.onPlayerUpdated()
                    .subscribe((e3) -> drawPlayerInfo(e3.getHealthList(), e3.getMaxHealthList(), e3.getExperience()));
            });
        });

        useXp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                controller.pressGameButton(Controller.Buttons.FIX, world);
            }
        });

        pauseAmbienceSound.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                sound.pauseSound(sound.getAmbienceClip());
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
                public void handle(final MouseEvent event) {
                    final Position p = new Position(c.getRow(), c.getColumn());
                    if (event.getButton() == MouseButton.SECONDARY) {
                        controller.action(p, world, Controller.States.SHOOTING);
                    } else {
                        controller.action(p, world, Controller.States.MOVING);
                    }
                }
            });
        });
        gridView.cellBorderColorProperty().set(CELL_BORDER_COLOR);
    }

    private void createEntity(final String name, final Position spawnPosition, final CardinalDirection spawnDirection) {
        drawImage(name, spawnPosition.row(), spawnPosition.column(), Optional.of(spawnDirection));
    }

    private void updateEntity(
        final String name,
        final Position oldPosition,
        final Position newPosition,
        final CardinalDirection newDirection) {
        removeEntity(oldPosition);
        drawImage(name, newPosition.row(), newPosition.column(), Optional.of(newDirection));
    }

    private void removeEntity(final Position lastPosition) {
        final int col = lastPosition.column();
        final int row = lastPosition.row();

        if (gridView.getCellPane(gridModel.getCell(col, row)).getChildren().size() == 0) {
            throw new IllegalArgumentException("Trying to delete cell view where there isn't anything");
        } else {
            gridView.getCellPane(gridModel.getCell(col, row)).getChildren().clear();
        }
    }

    private void drawImage(final String entityName, final int row, final int col, final Optional<CardinalDirection> d) {
        final URL imgPath = getClass().getResource(PATH_FUNC.apply(entityName.toLowerCase(Locale.ENGLISH)));
        if (imgPath == null) {
            throw new IllegalAccessError("Could not find image file");
        }
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
    }

    private void drawPlayerInfo(final List<Integer> healthList, final List<Integer> maxHealthList, final int experience) {
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
