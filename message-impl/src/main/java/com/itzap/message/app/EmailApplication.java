package com.itzap.message.app;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.buf.UDecoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 *
 */
public class EmailApplication
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailApplication.class);

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            LOGGER.error("ITZap Email application failed to run", e);
        }
    }

    private static void start() throws Exception {

        String contextPath = "";
        String appBase = ".";
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8025";
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getHost().setAppBase(appBase);

        Context context = tomcat.addWebapp(contextPath, appBase);

        Tomcat.addServlet(context, "jersey-container-servlet",
                new ServletContainer(resourceConfig()));
        context.addServletMappingDecoded(UDecoder.URLDecode("/v1/itzap/message/*", Charset.defaultCharset()),
                "jersey-container-servlet");

        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }

    private static ResourceConfig resourceConfig() {
        return new JerseyConfig();
    }
}
