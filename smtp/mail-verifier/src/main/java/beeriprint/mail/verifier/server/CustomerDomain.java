/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.server;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a domain name and its related customer codes.
 *
 * @author hilel
 */
public class CustomerDomain {

    private String domain;
    private Set<String> codes = new HashSet<>();

    public CustomerDomain(String domain, Set<String> codes) {
        this.domain = domain;
        this.codes = codes;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(domain).append(" / ");
        for (String code : codes) {
            b.append(code).append(" ");
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final CustomerDomain other = (CustomerDomain) obj;

        if (this.domain.equalsIgnoreCase(other.domain)) {
            return true;
        }

        for (String code : codes) {
            if (other.getCodes().contains(code)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.domain);
        hash = 53 * hash + Objects.hashCode(this.codes);
        return hash;
    }

    /**
     * @return the domain name
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain name to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return a collection of all customer codes related to this domain
     */
    public Set<String> getCodes() {
        return codes;
    }

    /**
     * @param codes the codes to set. set getCodes().
     */
    public void setCodes(Set<String> codes) {
        this.codes = codes;
    }
}
