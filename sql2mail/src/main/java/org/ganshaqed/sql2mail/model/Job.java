package org.ganshaqed.sql2mail.model;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.JobName;
import org.ganshaqed.sql2mail.data.DataProcessor;
import org.ganshaqed.sql2mail.statement.PreparedStatementParamSetter;

/**
 *
 * @author hilel
 */
public class Job {

    static final Logger LOGGER = Logger.getLogger(Job.class.getName());
    private final String name;
    private final Charset charset;
    private final Path outFolder;
    private final String query;
    private final PreparedStatementParamSetter paramSetter;
    private final DataProcessor dataProcessor;
    private final String subject;
    private final String body;
    private final String[] recipients;

    public Job(String jobName) throws Exception {
        this.name = jobName;
        LOGGER.log(Level.INFO, "Loading properties for job {0}", jobName);
        Properties p = new Properties();
        p.load(Job.class.getResourceAsStream("/jobs/" + jobName + ".properties"));
        // io properties
        charset = Charset.forName(p.getProperty("csv.file.encoding"));
        outFolder = Paths.get(p.getProperty("csv.report.folder"));
        // data properties
        query = p.getProperty("sql.query");
        paramSetter = (PreparedStatementParamSetter) Class.forName(p.getProperty("statement.param.setter")).newInstance();
        dataProcessor = (DataProcessor) Class.forName(p.getProperty("report.data.processor")).newInstance();
        // message properties
        subject = p.getProperty("message.subject");
        body = p.getProperty("message.body");
        recipients = p.getProperty("message.recipients").split(",");
    }

    public Path getReportFile() {
        return outFolder.resolve(name + ".csv");
    }

    /**
     * @return the charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return the paramSetter
     */
    public PreparedStatementParamSetter getParamSetter() {
        return paramSetter;
    }

    /**
     * @return the dataProcessor
     */
    public DataProcessor getDataProcessor() {
        return dataProcessor;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the recipients
     */
    public String[] getRecipients() {
        return recipients;
    }

    /**
     * @return the outFolder
     */
    public Path getOutFolder() {
        return outFolder;
    }
}
