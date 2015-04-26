package beeriprint.mail.verifier;

import beeriprint.mail.verifier.server.MessageRouter;
import beeriprint.mail.verifier.server.util.JdbcRuleBase;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    static SMTPServer server;

    public static void main(String[] args) {
        try {
            startServer();
        } catch (Exception ex) {
            logger.error("error initializing the server", ex);
        }
    }

    /**
     * Static method called by <a
     * href="http://commons.apache.org/proper/commons-daemon/procrun.html">prunsrv</a>
     * to start/stop the windows service.
     *
     * @param args "start" or "stop"
     */
    public static void windowsService(String args[]) {
        String cmd = "start";
        if (args.length > 0) {
            cmd = args[0];
        }
        try {
            if ("start".equals(cmd)) {
                startServer();
            } else {
                server.stop();
            }
        } catch (Exception ex) {
            logger.error(cmd, ex);
        }
    }

    /**
     * Initialize and start an instance of SMTPServer, with MessageRouter as its
     * MessageHandlerFactory
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static void startServer() throws IOException, ClassNotFoundException, SQLException {
        logger.trace("TRACE logging level enabled");
        logger.debug("DEBUG logging level enabled");
        logger.info("INFO logging level enabled");
        logger.warn("WARN logging level enabled");
        logger.error("ERROR logging level enabled");
        // load properties
        Properties properties = new Properties();
        properties.load(Main.class.getResourceAsStream("/smtp.properties"));
        String sourceServer = properties.getProperty("sourceServer");
        String targetServer = properties.getProperty("targetServer");
        logger.info("Source server: {}", sourceServer);
        logger.info("Target server: {}", targetServer);
        // create and start the server
        MessageRouter router = new MessageRouter(sourceServer, targetServer, new JdbcRuleBase());
        server = new SMTPServer(router);
        server.setHostName(properties.getProperty("hostName"));
        server.setPort(Integer.parseInt(properties.getProperty("port")));
        server.start();
        showInfo(server);
    }

    /**
     * Write to log some useful information about the running SMTPServer
     *
     * @param server
     */
    private static void showInfo(SMTPServer server) {
        logger.info("Max connections: {}", server.getMaxConnections());
        logger.info("Connection timeout (milliseconds): {}", server.getConnectionTimeout());
        logger.info("Max message size: {}", server.getMaxMessageSize());
    }
}
