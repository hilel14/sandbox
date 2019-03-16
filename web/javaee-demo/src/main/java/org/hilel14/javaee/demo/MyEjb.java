package org.hilel14.javaee.demo;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 *
 * @author hilel14
 */
@Stateless
public class MyEjb {

    static final Logger LOGGER = Logger.getLogger(MyEjb.class.getName());

    @Resource(name = "java:app/my.data.folder")
    private String dataFolder;

    @Asynchronous
    public void doLongTaskInBackground() {
        Calendar now = Calendar.getInstance();
        LOGGER.log(Level.INFO, "Start: {0}", now);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.SECOND, 20);
        LOGGER.log(Level.INFO, "End: {0}", end);
        while (now.before(end)) {
            now = Calendar.getInstance();
        }
        LOGGER.log(Level.INFO, "Task completed at {0}", now);
    }

}
