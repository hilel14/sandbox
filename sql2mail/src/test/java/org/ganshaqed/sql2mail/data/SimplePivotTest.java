package org.ganshaqed.sql2mail.data;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author hilel
 */
public class SimplePivotTest {

    @Test
    public void testProcessData() throws Exception {
        System.out.println("processData");
        List<List<String>> records = new ArrayList<>();

        List<String> record = new ArrayList<>();
        record.add("a");
        record.add("1");
        records.add(record);

        record = new ArrayList<>();
        record.add("a");
        record.add("2");
        records.add(record);

        record = new ArrayList<>();
        record.add("b");
        record.add("10");
        records.add(record);

        Path outFile = Paths.get("/var/opt/data/reports/test.csv");
        Charset charset = Charset.defaultCharset();
        SimplePivot instance = new SimplePivot();
        instance.processData(records, outFile, charset);
    }

}
