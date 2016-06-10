package my.fx.demo;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class FXMLController implements Initializable {

    static final Logger LOGGER = Logger.getLogger(FXMLController.class.getName());
    MyTask task;

    @FXML
    private TextField fileTextField;

    @FXML
    private Button runButton;

    @FXML
    private Button cancelButton;

    @FXML
    TextArea contentTextArea;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label statusLabel;

    @FXML
    private void fileButtonOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            fileTextField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void runButtonOnAction(ActionEvent event) {
        task = new MyTask(fileTextField.getText());
        task.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOGGER.info("The operation started");
                contentTextArea.setText("");
                runButton.setDisable(true);
                cancelButton.setDisable(false);
                progressIndicator.progressProperty().bind(task.progressProperty());
                statusLabel.textProperty().bind(task.messageProperty());
            }
        });
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOGGER.info("The operation completed successfully");
                List<String> results = task.getValue();
                contentTextArea.setText(results.toString());
                runButton.setDisable(false);
                cancelButton.setDisable(true);
            }
        });
        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                statusLabel.textProperty().unbind();
                statusLabel.setText("Task cancelled by user");
                runButton.setDisable(false);
                cancelButton.setDisable(true);
                progressIndicator.progressProperty().unbind();
                if (progressIndicator.getProgress() < 0) {
                    progressIndicator.setProgress(0);
                }
            }
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOGGER.log(Level.SEVERE, "Problem with my task", event.getSource().getException());
                contentTextArea.setText(event.getSource().getException().toString());
                runButton.setDisable(false);
                cancelButton.setDisable(true);
                statusLabel.textProperty().unbind();
                statusLabel.setText("Error occurred!");
                progressIndicator.progressProperty().unbind();
                if (progressIndicator.getProgress() < 0) {
                    progressIndicator.setProgress(0);
                }
            }
        });
        new Thread(task).start();
    }

    @FXML
    private void cancelButtonOnAction(ActionEvent event) {
        boolean result = task.cancel(true);
        LOGGER.log(Level.INFO,
                "An attempt to cancel execution to this task was {0}",
                result ? "successful" : "unsuccessful");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //double width = Double.parseDouble(preferences.get("Scene.width", "800"));
        //double height = Double.parseDouble(preferences.get("Scene.height", "600"));
        //borderPane.setPrefSize(800, 400);
    }
}
