package my.fx.demo;

import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());
    final Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        double width = preferences.getDouble("Scene.width", 800);
        double height = preferences.getDouble("Scene.height", 600);
        scene = new Scene(root, width, height);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("JavaFX Demo 2");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        preferences.putDouble("Scene.width", scene.getWidth());
        preferences.putDouble("Scene.height", scene.getHeight());
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
