package com.itzap.message.app;

import com.itzap.message.config.Factories;
import com.itzap.message.services.EMailService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.inject.hk2.ImmediateHk2InjectionManager;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import javax.inject.Singleton;

import static org.glassfish.hk2.extras.ExtrasUtilities.enableTopicDistribution;

/**
 * Created by avinokurov on 2/9/2017.
 */

public class JerseyConfig extends ResourceConfig {
    JerseyConfig() {
        register(JacksonFeature.class);

        // register endpoints
        packages("com.itzap.message.rest");

        register(new ContainerLifecycleListener()
        {
            public void onStartup(Container container)
            {
                // access the ServiceLocator here
                // serviceLocator = container.getApplicationHandler().
                ServiceLocator serviceLocator = ((ImmediateHk2InjectionManager)container
                        .getApplicationHandler().getInjectionManager()).getServiceLocator();
                enableTopicDistribution(serviceLocator);
                // ... do what you need with ServiceLocator ...
            }

            public void onReload(Container container) {/*...*/}
            public void onShutdown(Container container) {/*...*/}
        });

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(Factories.MailServiceFactory.class)
                        .to(EMailService.class)
                        .in(Singleton.class);
            }
        });
    }
}
