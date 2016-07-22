package beeriprint.newsletter.batch.mailer.processors;

import beeriprint.newsletter.batch.mailer.model.Email;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 *
 * @author hilel
 */
public class NewsletterImporter extends BasicImporter {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(NewsletterImporter.class);
    private final List<String> unsubscribers;

    public NewsletterImporter(String templateName, String campaignDate, String listFile, JdbcTemplate jdbcTemplate) {
        super(templateName, campaignDate, listFile, jdbcTemplate);
        unsubscribers = findUnsubscribers();
    }

    @Override
    public Email process(Email item) throws Exception {
        // filter recipients with NotActive state id from ActiveTrail
        if (item.getRecipientState().equalsIgnoreCase("NotActive")) {
            LOGGER.info("Skipping recipient " + item.getRecipientAddress() + " (not active)");
            return null;
        }
        // filter recipients who asked to unsubscribe in the past
        if (unsubscribers.contains(item.getRecipientAddress())) {
            LOGGER.info("Skipping recipient " + item.getRecipientAddress() + " (unsubscribed)");
            return null;
        }
        // include all other recipients
        importItem(item);
        return item;
    }

    private List<String> findUnsubscribers() {
        String sql
                = "SELECT DISTINCT(CONCAT(user_part, '@', domain_part)) AS email "
                + "FROM activity, email "
                + "WHERE activity_type = 'unsubscribe' "
                + "AND activity.email_id = email.id";
        RowMapper rowMapper = new SingleColumnRowMapper();
        ResultSetExtractor resultSetExtractor = new RowMapperResultSetExtractor(rowMapper);
        List<String> unsubscribedRecipients = (List<String>) getJdbcTemplate().query(sql, resultSetExtractor);
        LOGGER.info(unsubscribedRecipients.size() + " unsubscribed recipients found");
        return unsubscribedRecipients;
    }
}
