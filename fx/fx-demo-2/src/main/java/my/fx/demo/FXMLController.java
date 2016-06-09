package my.fx.demo;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class FXMLController implements Initializable {

    static final Logger LOGGER = Logger.getLogger(FXMLController.class.getName());

    @FXML
    private TextField fileTextField;

    @FXML
    TextArea contentTextArea;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private TextField statusTextField;

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
        final MyTask task = new MyTask(fileTextField.getText());
        task.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOGGER.info("The operation started");
                contentTextArea.setText("");
            }
        });
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOGGER.info("The operation completed successfully");
                List<String> results = task.getValue();
                contentTextArea.setText(results.toString());
            }
        });
        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOGGER.log(Level.SEVERE, "Problem with my task", event.getSource().getException());
                contentTextArea.setText(event.getSource().getException().toString());
            }
        });
        progressBar.progressProperty().bind(task.progressProperty());
        progressIndicator.progressProperty().bind(task.progressProperty());
        statusTextField.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
