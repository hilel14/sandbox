package org.ganshaqed.sql2mail;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.ganshaqed.sql2mail.model.Job;

/**
 *
 * @author hilel
 */
public class Main {

    static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Options options = new Options();
        addOptions(options);
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            //LOGGER.log(Level.INFO, "The application launched with the following arguments: {0}", commandLine.getOptions());
            String jobName = commandLine.getOptionValue("job").trim();
            String params = commandLine.getOptionValue("statement-params");
            Main main = new Main();
            main.runJob(jobName, params);
            LOGGER.info("The operation completed successfully");
        } catch (ParseException ex) {
            new HelpFormatter().printHelp("java -cp sql2mail.jar " + Main.class.getCanonicalName(), options, true);
            System.exit(1);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            System.exit(1);
        }

    }

    public void runJob(String jobName, String statementParams) throws Exception {
        Job job = new Job(jobName);
        JdbcController controller = new JdbcController();
        List<List<String>> records = controller.exportData(job, statementParams);
        job.getDataProcessor().processData(records, job.getReportFile(), job.getCharset());
        EmailSender sender = new EmailSender();
        sender.sendEmail(job);
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
