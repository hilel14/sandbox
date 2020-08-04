package org.hilel14.javaee.demo;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author hilel14
 */
@javax.ws.rs.ApplicationPath("webapi")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.hilel14.javaee.demo.MyResource.class);
    }
}
