package org.ganshaqed.sql2mail.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author hilel
 */
public class DefaultParamSetter implements PreparedStatementParamSetter {

    static final Logger LOGGER = Logger.getLogger(DefaultParamSetter.class.getName());

    @Override
    public void setParams(PreparedStatement statement, String[] params) throws SQLException {
        LOGGER.info("No params found");
    }

}
