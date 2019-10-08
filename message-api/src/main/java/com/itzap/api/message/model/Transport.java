package com.itzap.api.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel
public enum Transport {
    EMAIL("email");

    private final String value;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageStatus.class);

    Transport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Transport toTransport(String strTransport) {
        for (Transport t: values()) {
            if (t.value.equalsIgnoreCase(strTransport) ||
                    t.name().equalsIgnoreCase(strTransport)) {
                return t;
            }
        }

        LOGGER.warn("Failed to find message transport string {}. Default to EMAIL", strTransport);
        return EMAIL;
    }
}
