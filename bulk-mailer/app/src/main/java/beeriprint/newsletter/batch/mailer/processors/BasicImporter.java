package beeriprint.newsletter.batch.mailer.processors;

import beeriprint.newsletter.batch.mailer.model.Campaign;
import beeriprint.newsletter.batch.mailer.model.Email;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hilel
 */
public class BasicImporter implements ItemProcessor<Email, Email> {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BasicImporter.class);
    private final Campaign campaign;
    private final JdbcTemplate jdbcTemplate;

    public BasicImporter(String templateName, String campaignDate, String listFile, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        campaign = findCampaign(templateName, campaignDate, listFile);
    }

    @Override
    public Email process(Email item) throws Exception {
        importItem(item);
        return item;
    }

    private Campaign findCampaign(String templateName, String campaignDate, String listFile) {
        String fileName = Paths.get(listFile).getFileName().toString();
        String sqlSelect = "SELECT * FROM campaign WHERE template_name = ? AND campaign_date = ? AND list_file = ?";
        Object[] args = new Object[]{templateName, campaignDate, fileName};
        LOGGER.info("Campaign params = " + Arrays.toString(args));
        List<Campaign> campaigns = getJdbcTemplate().query(sqlSelect, args, BeanPropertyRowMapper.newInstance(Campaign.class));
        switch (campaigns.size()) {
            case 0:
                LOGGER.info("Creating new campaign record");
                String sqlUpdate = "INSERT INTO campaign (template_name, campaign_date, list_file) VALUES (?,?,?)";
                getJdbcTemplate().update(sqlUpdate, args);
                campaigns = getJdbcTemplate().query(sqlSelect, args, BeanPropertyRowMapper.newInstance(Campaign.class));
                break;
            case 1:
                LOGGER.info("Existing campaign record found with id = " + campaigns.get(0).getId());
                break;
            default:
                throw new IllegalArgumentException(campaigns.size() + " campaigns found, but only 1 was expected");
        }
        return campaigns.get(0);
    }

    public void importItem(Email item) {
        item.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        item.setCampaign(getCampaign());
        item.setCreationTime(new Date());
        String sql
                = "INSERT INTO email (id, campaign_id, recipient_name, user_part, domain_part, created) "
                + "VALUES (?,?,?,?,?,?)";
        getJdbcTemplate().update(sql, item.valuesAsArray());
    }

    /**
     * @return the campaign
     */
    public Campaign getCampaign() {
        return campaign;
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
