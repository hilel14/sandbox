package org.my.spring.batch.java.config.demo.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.batch.core.ItemProcessListener;

/**
 * @author Tobias Flohre
 */
public class LogProcessListener implements ItemProcessListener<Object, Object> {

    static final Logger logger = Logger.getLogger(LogProcessListener.class.getName());

    @Override
    public void afterProcess(Object item, Object result) {
        logger.log(Level.INFO, "Input to Processor: {0}", item.toString());
        logger.log(Level.INFO, "Output of Processor: {0}", result.toString());
    }

    @Override
    public void beforeProcess(Object item) {
    }

    @Override
    public void onProcessError(Object item, Exception e) {
    }

}
