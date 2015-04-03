/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.server;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author hilel
 */
public interface RuleBase {

    public Map<String, Set<String>> getDomainToCustomer();

    public Set<String> getSenderWhiteList();

    public Set<String> getDomainWhiteList();

    public Set<Pattern> getSpecialAttachmentNames();
}
