package beeriprint.newsletter.camel.processors;

import beeriprint.newsletter.model.UserActivity;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author hilel
 */
public class PrepareSelectEmail implements Processor {

    final String selectEmailByMessage
            = "SELECT user_part, domain_part FROM email WHERE id = ?";

    @Override
    public void process(Exchange exchange) throws Exception {
        UserActivity userActivity = exchange.getIn().getBody(UserActivity.class);
        Map jdbcParameters = new HashMap();
        jdbcParameters.put("messageCode", userActivity.getMessageCode());
        exchange.getOut().setHeader("newsletter.userActivity", userActivity);
        exchange.getOut().setHeader("CamelJdbcParameters", jdbcParameters);
        exchange.getOut().setBody(selectEmailByMessage);
    }
}
