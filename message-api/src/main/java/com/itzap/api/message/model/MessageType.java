package com.itzap.api.message.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel
public enum MessageType {
    HTML("html"),
    TEXT("text");

    private final String value;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageStatus.class);

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MessageType toType(String strType) {
        for (MessageType t: values()) {
            if (t.value.equalsIgnoreCase(strType) ||
                    t.name().equalsIgnoreCase(strType)) {
                return t;
            }
        }

        LOGGER.warn("Failed to find message type string {}. Default to TEXT", strType);
        return TEXT;
    }
}
