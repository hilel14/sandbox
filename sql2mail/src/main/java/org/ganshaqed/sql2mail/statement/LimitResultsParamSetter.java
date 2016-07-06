package org.ganshaqed.sql2mail.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hilel
 */
public class LimitResultsParamSetter implements ParamSetter {

    static final Logger LOGGER = Logger.getLogger(LimitResultsParamSetter.class.getName());

    /**
     *
     * @param statement
     * @param params
     * @throws SQLException
     */
    @Override
    public void setParams(PreparedStatement statement, String[] params)
            throws SQLException {
        LOGGER.log(Level.INFO, "Setting results limit to {0} rows", params[0]);
        int limit = Integer.parseInt(params[0]);
        statement.setInt(1, limit);
    }

}
