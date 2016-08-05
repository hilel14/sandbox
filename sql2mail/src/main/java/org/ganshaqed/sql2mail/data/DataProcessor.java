package org.ganshaqed.sql2mail.data;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author hilel
 */
public interface DataProcessor {

    public void processData(List<List<String>> records, Path outFile, Charset charset) throws Exception;
}
