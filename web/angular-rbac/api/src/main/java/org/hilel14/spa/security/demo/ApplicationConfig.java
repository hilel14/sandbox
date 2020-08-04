package org.hilel14.spa.security.demo;

import io.jsonwebtoken.impl.crypto.MacProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author hilel
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jwt.key", MacProvider.generateKey());
        return properties;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(org.hilel14.spa.security.demo.AuthenticationFilter.class);
        resources.add(org.hilel14.spa.security.demo.GenericResource.class);
    }

}
