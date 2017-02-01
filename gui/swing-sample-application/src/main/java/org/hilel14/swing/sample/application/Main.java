package org.hilel14.swing.sample.application;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hilel
 */
public class Main implements ProgressListener {

    static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Mandatory arguments: input output");
            System.exit(1);
        }
        Path inFile = Paths.get(args[0]);
        Path outFile = Paths.get(args[1]);
        try {
            FileProcessor processor = new FileProcessor(new Main());
            processor.processFile(inFile, outFile);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
    }

    @Override
    public void showProgress(int currentLine, int totalLines) {
        if (currentLine % 100 == 0) {
            System.out.println("Line " + currentLine + " of " + totalLines);
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
