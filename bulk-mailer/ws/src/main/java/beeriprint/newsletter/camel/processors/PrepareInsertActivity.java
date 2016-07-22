package beeriprint.newsletter.camel.processors;

import beeriprint.newsletter.model.UserActivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hilel
 */
public class PrepareInsertActivity implements Processor {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final String sql
            = "INSERT INTO activity (email_id, activity_time, activity_type, resource_id) "
            + "VALUES (:?messageCode, :?requestTime, :?activityType, :?resourceId)";

    @Override
    public void process(Exchange exchange) throws Exception {
        UserActivity activity = exchange.getIn().getHeader("newsletter.userActivity", UserActivity.class);
        String requestTime = dateFormat.format(activity.getRequestTime().getTime());
        Map jdbcParameters = new HashMap();
        jdbcParameters.put("messageCode", activity.getMessageCode());
        jdbcParameters.put("resourceId", activity.getResourceId());
        jdbcParameters.put("activityType", activity.getActivityType());
        jdbcParameters.put("requestTime", requestTime);
        exchange.getOut().setHeader("CamelJdbcParameters", jdbcParameters);
        exchange.getOut().setBody(sql);
    }
}
