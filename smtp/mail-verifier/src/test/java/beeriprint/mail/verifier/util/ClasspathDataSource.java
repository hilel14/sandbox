/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author hilel
 */
public class ClasspathDataSource implements javax.activation.DataSource {

    String classPath;
    String contentType;
    String name;

    public ClasspathDataSource(String classPath, String contentType, String name) {
        this.classPath = classPath;
        this.contentType = contentType;
        this.name = name;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return ClasspathDataSource.class.getResourceAsStream(classPath);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
        return name;
    }

}
