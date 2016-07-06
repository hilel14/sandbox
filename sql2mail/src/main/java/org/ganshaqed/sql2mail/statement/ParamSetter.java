package org.ganshaqed.sql2mail.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author hilel
 */
public interface ParamSetter {

    public void setParams(PreparedStatement statement, String[] params)
            throws SQLException;
}
