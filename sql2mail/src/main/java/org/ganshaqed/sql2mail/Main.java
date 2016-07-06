package org.ganshaqed.sql2mail;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.mail.EmailException;
import org.ganshaqed.sql2mail.statement.PreparedStatementParamSetter;

/**
 *
 * @author hilel
 */
public class Main {

    static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    Path reportFolder;
    Charset charset;
    String query;
    PreparedStatementParamSetter paramSetter;
    String subject;
    String body;
    String[] recipients;
    Path reportFile;
    String[] statementParams;

    public static void main(String[] args) {
        Options options = new Options();
        addOptions(options);
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            String job = commandLine.getOptionValue("job");
            String params = commandLine.getOptionValue("statement-params");
            Main main = new Main(job, params);
            main.exportData();
            main.sendMail();
            LOGGER.info("The operation completed successfully");
        } catch (ParseException ex) {
            new HelpFormatter().printHelp("java -cp sql2mail.jar " + Main.class.getCanonicalName(), options);
            System.exit(1);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            System.exit(1);
        }

    }

    public Main(String jobName, String statementParams)
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Properties p = new Properties();

        LOGGER.log(Level.INFO, "Loading application properties");
        p.load(Main.class.getResourceAsStream("/application.properties"));
        reportFolder = Paths.get(p.getProperty("csv.report.folder"));
        reportFile = reportFolder.resolve(jobName + ".csv");

        LOGGER.log(Level.INFO, "Loading properties for job {0}", jobName);
        p = new Properties();
        p.load(Main.class.getResourceAsStream("/jobs/" + jobName + ".properties"));
        // general properties
        charset = Charset.forName(p.getProperty("csv.file.encoding"));
        // sql properties
        query = p.getProperty("sql.query");
        paramSetter = (PreparedStatementParamSetter) Class.forName(p.getProperty("statement.param.setter")).newInstance();
        // message properties
        subject = p.getProperty("message.subject");
        body = p.getProperty("message.body");
        recipients = p.getProperty("message.recipients").split(",");

        if (statementParams != null) {
            LOGGER.log(Level.INFO, "Parsing statement params {0}", statementParams);
            this.statementParams = statementParams.split(",");
        }
    }

    public void exportData() throws IOException, SQLException {
        JdbcController controller = new JdbcController();
        controller.exportData(reportFile, charset, query, paramSetter, statementParams);
    }

    public void sendMail() throws IOException, EmailException, MessagingException {
        EmailSender sender = new EmailSender();
        sender.sendEmail(reportFile, recipients, subject, body);
    }

    private static void addOptions(Options options) {
        Option option;
        // job
        option = new Option("j", "job", true, "job properties file");
        option.setRequired(true);
        option.setArgs(1);
        options.addOption(option);
        // statement-params
        option = new Option("p", "statement-params", true, "prepared statement params");
        option.setRequired(false);
        options.addOption(option);
    }
}
