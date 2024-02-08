package it.unibo.object_onepiece.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public final class SceneControllerImpl implements SceneController {

    private Scene currentScene;
    private final Stage primaryStage;

    private final String stageTitle = "Object One Piece!";

    public SceneControllerImpl(Scene currentScene, Stage primaryStage) {
        this.currentScene = currentScene;
        this.primaryStage = primaryStage;
        MainMenu mainMenu = new MainMenu(this);
        currentScene.setRoot(mainMenu.setUp());
        currentScene.getStylesheets().add(mainMenu.styleSheet);

        primaryStage.setTitle(stageTitle);
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    @Override
    public void switchToGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchToGame'");
    }

    @Override
    public void closeScene() {
        var stage = (Stage) currentScene.getWindow();
        stage.close();
    }

}
