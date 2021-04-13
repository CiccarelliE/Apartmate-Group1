import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {

        //loads first UI
        Parent root = FXMLLoader.load(getClass().getResource("/VIEW/LoginUI.fxml"));
        Scene scene = new Scene(root);

        //makes the scene visisble and sets title
        stage.setTitle("Apartmate");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
