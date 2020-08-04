package org.hilel14.spa.security.demo;

/**
 *
 * @author hilel14
 */
public class AuthenticationEngine {

    public String getRole(String user, String password) {
        if (user.startsWith("m")) {
            return "manager";
        }
        if (user.startsWith("e")) {
            return "employee";
        }
        return "guest";
    }

}
