package my.fx.demo;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.concurrent.Task;

/**
 *
 * @author hilel
 */
public class MyTask extends Task<List<String>> {

    Path file;

    public MyTask(String path) {
        this.file = Paths.get(path);
    }

    @Override
    protected List<String> call() throws Exception {
        updateMessage("Preparing to process " + file.getFileName());
        updateProgress(-1, 1);
        long max = Files.size(file);
        Thread.sleep(3000);
        updateMessage("Task started...");
        List<String> lines = Files.readAllLines(file, Charset.defaultCharset());
        for (int i = 1; i <= max; i++) {
            if (isCancelled()) {
                break;
            }
            updateProgress(i, max);
            Thread.sleep(10);
        }
        updateMessage("Task completed!");
        return lines;
    }
}
