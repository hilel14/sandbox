/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hilel
 */
public class Sandbox {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Sandbox.class);

    public static void main(String[] args) throws Exception {
        f5();
    }

    static void f1() throws Exception {
        InternetAddress address = new InternetAddress("\"aa\" <bb@cc.com>");
        String s = address.getAddress();
        System.out.println(s);
        address = new InternetAddress("bb@cc.com");
        s = address.getAddress();
        System.out.println(s);
    }

    static void f2() throws Exception {
        String s = MimeUtility.decodeText("=?windows-1255?B?NDQyIOP46iDg+PUg+un36e0g4frk7OnqIOnw5eD4IDIwMTUueGxz?=");
        System.out.println(s);
        System.out.println(s.startsWith("442"));

        String s2 = MimeUtility.decodeText("=?utf-8?B?MzgwLdeQ15nXkdeZ15wgLSDXoNeV16nXkCDXlNee15nXmdecLnhsc3g=?=");
        System.out.println(s2.startsWith("380"));

        String s3 = MimeUtility.decodeText("Gen000A001");
        System.out.println(s3);

        //String s4 = MimeUtility.decodeWord("Gen000A001");
    }

    static void f3() {
        Set<String> set = new HashSet<>();
        set.add("aaa");
        set.add("bbb");
        System.out.println(set.toString());
        logger.info(set.toString());
    }

    static void f4() {
        List<String> recipients = new ArrayList<>();
        recipients.add("aaa");
        recipients.add("bbb");
        System.out.println(recipients.toString());
    }

    static void f5() throws MessagingException, UnsupportedEncodingException {
        MimeMessage m = new MimeMessage(Session.getDefaultInstance(System.getProperties()));
        m.setFrom(new InternetAddress("user0", "test.com"));
        m.addRecipients(Message.RecipientType.TO, "user1@test.com");
        //InternetAddress address = new InternetAddress("'user2@test.com'", false);
        //m.addRecipients(Message.RecipientType.CC, address.getAddress());
        //m.setHeader("Cc", "User Two <'user2@test.com'>");
        //System.out.println(Arrays.toString(m.getAllRecipients()));
        printAllRecipients(m);
    }

    /*
     private Address[] getAddressHeader(String name) throws MessagingException {
     String s = getHeader(name, ","); // name = To, Cc, Bcc
     return (s == null) ? null : InternetAddress.parseHeader(s, strict);
     }
     */
    static void printAllRecipients(MimeMessage m) throws MessagingException {
        String h = m.getHeader("Cc", ","); // name = To, Cc, Bcc
        InternetAddress[] cc = InternetAddress.parseHeader(h, false);
        for (InternetAddress address : cc) {
            String s = address.getAddress();            
            System.out.println(s);
        }
    }

    static void showLogLevels() {
        logger.trace("1 trace level");
        logger.debug("2 debug level");
        logger.info("3 info level");
        logger.warn("4 warn level");
        logger.error("5 error level");
    }

}
