/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.util;

import beeriprint.mail.verifier.server.RuleBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hilel
 */
public class EmbeddedRuleBase implements RuleBase {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedRuleBase.class);

    @Override
    public Map<String, Set<String>> getDomainToCustomer() {
        Set<String> customerCodes;
        Map<String, Set<String>> domainToCustomer = new HashMap<>();
        // domain1a.co.il
        customerCodes = new HashSet<>();
        customerCodes.add("001");
        customerCodes.add("002");
        customerCodes.add("003");
        domainToCustomer.put("bll.co.il", customerCodes);
        // domain1b.co.il
        customerCodes = new HashSet<>();
        customerCodes.add("001");
        customerCodes.add("011");
        customerCodes.add("012");
        // all done
        return domainToCustomer;
    }

    @Override
    public Set<String> getSenderWhiteList() {
        Set<String> senders = new HashSet<>();
        senders.add("user1@beeriprint.co.il");
        return senders;
    }

    @Override
    public Set<String> getDomainWhiteList() {
        Set<String> domains = new HashSet<>();
        domains.add("beeriprint.co.il");
        domains.add("beeridigital.com");
        return domains;
    }

    @Override
    public Set<Pattern> getSpecialAttachmentNames() {
        Set<Pattern> patterns = new HashSet<>();
        try (InputStream in = EmbeddedRuleBase.class.getClassLoader().getResourceAsStream("special-attachment-names.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                patterns.add(Pattern.compile(line));
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return patterns;
    }

}
