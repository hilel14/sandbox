package my.sandbox.spring.batch.demo;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        /**
         * Setup:
         *
         * Create batch database and tables
         *
         * Create people table in test database
         *
         * Create user1 with password 123456
         *
         * Grant user1 access to batch database and test database
         *
         */
        args = new String[]{"classpath:/spring/job-1.xml", "job-1", "inputFile=/var/opt/data/sample-data-2.csv", "my.time=" + new Date().toString()};

        try {
            org.springframework.batch.core.launch.support.CommandLineJobRunner.main(args);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
