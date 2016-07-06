package org.ganshaqed.sql2mail.statement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hilel
 */
public class LastDaysParamSetter implements PreparedStatementParamSetter {

    static final Logger LOGGER = Logger.getLogger(LastDaysParamSetter.class.getName());

    @Override
    public void setParams(PreparedStatement statement, String[] params) throws SQLException {
        int days = Integer.parseInt(params[0]);
        LOGGER.log(Level.INFO, "Prepare to get results from last {0} days", days);
        // start date = 7 days ago at midnight
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        LOGGER.log(Level.INFO, "Set start date to {0}", calendar.getTime());
        statement.setDate(1, new Date(calendar.getTimeInMillis()));
        // end date = yesterday at 23:59:59
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        LOGGER.log(Level.INFO, "Set end date to {0}", calendar.getTime());
        statement.setDate(2, new Date(calendar.getTimeInMillis()));
    }

}
