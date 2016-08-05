package org.ganshaqed.sql2mail.data;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import static org.ganshaqed.sql2mail.data.DefaultDataProcessor.LOGGER;

/**
 *
 * @author hilel
 */
public class SimplePivot implements DataProcessor {

    static final Logger LOGGER = Logger.getLogger(SimplePivot.class.getName());

    @Override
    public void processData(List<List<String>> records, Path outFile, Charset charset) throws Exception {
        LOGGER.log(Level.INFO, "Processing {0} records", records.size());
        Map<String, Double> data = new HashMap<>();
        for (List<String> record : records) {
            String key = record.get(0);
            Double val = Double.parseDouble(record.get(1));
            Double total = data.get(key) == null ? val : data.get(key) + val;
            data.put(key, total);
        }
        records = mapToList(data);
        saveResults(records, outFile, charset);
    }

    private List<List<String>> mapToList(Map<String, Double> data) {
        List<List<String>> records = new ArrayList<>();
        for (String key : data.keySet()) {
            List<String> record = new ArrayList<>();
            record.add(key);
            record.add(String.valueOf(data.get(key)));
            records.add(record);
        }
        return records;
    }

    private void saveResults(List<List<String>> records, Path outFile, Charset charset) throws Exception {
        LOGGER.log(Level.INFO, "Saving {0} records to file {1}", new Object[]{records.size(), outFile});
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outFile.toFile()), charset);
                CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
            printer.printRecords(records);
        }
    }

}
