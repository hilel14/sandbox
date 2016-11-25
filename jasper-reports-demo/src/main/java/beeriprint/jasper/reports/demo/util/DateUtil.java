/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.jasper.reports.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 *
 * @author Hilel
 */
public class DateUtil {

    static final Logger LOGGER = Logger.getLogger(DateUtil.class.getName());
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String grandmaBirthday() {
        return "1900-01-01";
    }
}
