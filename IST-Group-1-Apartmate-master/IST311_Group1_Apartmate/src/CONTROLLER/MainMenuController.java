package CONTROLLER;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class MainMenuController {

    private static MainMenuController mainMenuController;
    private Stage stage;

    private MainMenuController(Stage stage) {
        this.stage = stage;
        setupView();
        stage.show();
    }

    public void setupView() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/VIEW/MainMenuUI.fxml"));
            Scene menuScene = new Scene(parent);
            stage.setScene(menuScene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }

    public static MainMenuController getMainMenuController(Stage stage) {
        if (mainMenuController == null) {
            mainMenuController = new MainMenuController(stage);
        }
        return mainMenuController;
    }

}
