package my.fx.demo;

import javafx.concurrent.Task;

/**
 *
 * @author hilel
 */
public class MyTask extends Task<Void> {

    @Override
    protected Void call() throws Exception {
        final int max = 10;
        updateMessage("Task started...");
        for (int i = 1; i <= max; i++) {
            Thread.sleep(200);
            if (isCancelled()) {
                break;
            }
            updateProgress(i, max);
        }
        updateMessage("Task completed!");
        return null;
    }
}
