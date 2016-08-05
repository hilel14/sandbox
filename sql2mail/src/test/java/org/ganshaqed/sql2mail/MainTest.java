package org.ganshaqed.sql2mail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hilel
 */
public class MainTest {

    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //@Test
    public void testJob1() {
        String[] args = new String[]{"--job=job1"};
        Main.main(args);

    }

    //@Test
    public void testJob2() {
        String[] args = new String[]{"--job=job2", "--statement-params=3"};
        Main.main(args);
    }

    @Test
    public void testJob3() {
        String[] args = new String[]{"--job=job3", "--statement-params=365"};
        Main.main(args);
    }

}
