package com.udacity.jwdnd.course1.cloudstorage.security;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * With Tomcat server version 7 and above, the maxSwallowSize property specifies the max number of bytes
 * tomcat will swallow or send to the server when it knows the server will ignore the file.
 * Setting the maxSwallowSize to a negative value instructs tomcat swallow all failed uploads regardless
 * of file size. In this way the application can handle file upload exceptions directly.*/
@Component
public class TomcatConfig implements
        WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {

        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                if(connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
                }
            }
        });
    }
}