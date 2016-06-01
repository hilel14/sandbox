package my.fx.demo;

import java.io.File;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author hilel
 */
public class MyFxApp extends Application {

    final Preferences preferences = Preferences.userNodeForPackage(Application.class);

    Scene scene;

    Text scenetitle = new Text("My File Processor");
    Label fileLable = new Label("File");
    TextField fileTextField = new TextField();
    Button fileButton = new Button("Open");
    ProgressBar progressBar = new ProgressBar(0.3);
    Label statusLable = new Label("Status...");
    Button runButton = new Button("Run");

    @Override
    public void start(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        scenetitle.setFont(Font.font("sans-serif", FontWeight.NORMAL, 18));
        GridPane.setHgrow(fileTextField, Priority.ALWAYS);
        //progressBar.prefWidthProperty().bind(fileTextField.prefWidthProperty());
        //progressBar.maxWidth(Double.MAX_VALUE);
        //GridPane.setHgrow(progressBar, Priority.ALWAYS);
        fileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                fileButtonOnAction(event);
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                runButtonOnAction(event);
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                primaryStageOnCloseRequest(event);
            }
        });

        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(fileLable, 0, 1);
        grid.add(fileTextField, 1, 1);
        grid.add(fileButton, 2, 1);
        grid.add(new HBox(10, progressBar, statusLable), 1, 2);
        //grid.add(statusLable, 0, 2);
        //grid.add(progressBar, 1, 2);
        grid.add(runButton, 2, 2);

        grid.setGridLinesVisible(false);
        BorderPane borderPane = new BorderPane(grid);
        double width = Double.parseDouble(preferences.get("Scene.width", "800"));
        double height = Double.parseDouble(preferences.get("Scene.height", "200"));
        scene = new Scene(borderPane, width, height);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fileButtonOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            fileTextField.setText(file.getAbsolutePath());
        }
    }

    private void runButtonOnAction(ActionEvent event) {
        MyTask task = new MyTask();
        progressBar.progressProperty().bind(task.progressProperty());
        statusLable.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }

    private void primaryStageOnCloseRequest(WindowEvent event) {
        preferences.put("Scene.width", String.valueOf(scene.getWidth()));
        preferences.put("Scene.height", String.valueOf(scene.getHeight()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
