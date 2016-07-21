package beeriprint.newsletter.camel.processors;

import beeriprint.newsletter.model.DsnRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author hilel
 */
public class PrepareUpdateEmail implements Processor {

    final String sql
            = "UPDATE email SET action_code  = :?actionCode, status_code = :?statusCode, diagnostic_code = :?diagnosticCode "
            + "WHERE id = :?messageCode";

    @Override
    public void process(Exchange exchange) throws Exception {
        DsnRecord record = exchange.getIn().getBody(DsnRecord.class);
        exchange.getOut().setHeader("CamelJdbcParameters", record.asMap());
        exchange.getOut().setBody(sql);
    }

}
