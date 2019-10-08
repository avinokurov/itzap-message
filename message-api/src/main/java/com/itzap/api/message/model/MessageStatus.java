package com.itzap.api.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel
public enum MessageStatus {
    SENT("sent"),
    ERROR("error"),
    UNKNOWN("unknown");

    private final String value;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageStatus.class);

    MessageStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MessageStatus toMessageStatus(String strMessageStatus) {
        for (MessageStatus t: values()) {
            if (t.value.equalsIgnoreCase(strMessageStatus) ||
                    t.name().equalsIgnoreCase(strMessageStatus)) {
                return t;
            }
        }

        LOGGER.warn("Failed to find message status string {}. Default to UNKNOWN",
                strMessageStatus);
        return UNKNOWN;
    }
}
