package com.itzap.message.config;

import com.itzap.message.services.EMailService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public final class Factories {
    private static final Config CONFIG = ConfigFactory.load();

    private Factories() {}

    public static class MailServiceFactory extends AbstractFactory<EMailService> {
        public MailServiceFactory() {
        }

        @Override
        public EMailService provide() {
            return new EMailService(CONFIG);
        }
    }
}
