package beeriprint.newsletter.ws;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author hilel-deb
 */
public class JmsClient {

    Logger logger = Logger.getLogger(JmsClient.class.getName());
    ConnectionFactory connectionFactory;

    public JmsClient(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void produce(String text, String queueName) throws JMSException {

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue(queueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
    }
}
