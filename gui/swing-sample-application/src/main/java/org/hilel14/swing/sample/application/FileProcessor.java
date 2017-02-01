package org.hilel14.swing.sample.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hilel-deb
 */
public class FileProcessor {

    static final Logger LOGGER = Logger.getLogger(FileProcessor.class.getName());
    ProgressListener listener;
    Charset charset;

    public FileProcessor(ProgressListener listener) throws IOException {
        this.listener = listener;
        setup();
    }

    private void setup() throws IOException {
        InputStream in = FileProcessor.class.getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(in);
        charset = Charset.forName(props.getProperty("charset"));
    }

    public void processFile(Path inFile, Path outFile) throws IOException {
        LOGGER.log(Level.INFO, "input file = {0}", inFile);
        listener.showMessage("analyzing " + inFile);
        int totalLines = countLines(inFile);
        listener.showMessage("processing " + inFile);
        int currentLine = 0;
        String line;
        try (BufferedReader reader = Files.newBufferedReader(inFile, charset);
                BufferedWriter writer = Files.newBufferedWriter(outFile, charset, StandardOpenOption.CREATE)) {
            while ((line = reader.readLine()) != null) {
                currentLine++;
                listener.showProgress((int) (currentLine * 100 / totalLines));
                String data = porcessLine(line);
                writer.write(data);
                writer.newLine();
            }
        }
        listener.showMessage("The operation completed successfully");
        LOGGER.log(Level.INFO, "The operation completed successfully");
    }

    private int countLines(Path inFile) throws IOException {
        int totalLines = 0;
        try (BufferedReader reader = Files.newBufferedReader(inFile, charset);) {
            while ((reader.readLine()) != null) {
                totalLines++;
            }
        }
        return totalLines;
    }

    private String porcessLine(String line) {
        /*
         try {
         Thread.sleep(1);
         } catch (InterruptedException ex) {
         Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        return line.toUpperCase();
    }

}
