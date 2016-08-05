package org.ganshaqed.sql2mail.data;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author hilel
 */
public class DefaultDataProcessor implements DataProcessor {

    static final Logger LOGGER = Logger.getLogger(DefaultDataProcessor.class.getName());

    @Override
    public void processData(List<List<String>> records, Path outFile, Charset charset) throws Exception {
        LOGGER.log(Level.INFO, "Saving {0} records to file {1}", new Object[]{records.size(), outFile});
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outFile.toFile()), charset);
                //CSVPrinter printer = CSVFormat.DEFAULT.withHeader(records).print(out)) {
                CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
            printer.printRecords(records);
        }
    }

}
