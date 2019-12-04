package com.itzap.api.message.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema
@JsonDeserialize(builder = Message.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private final String messageId;
    private final String subject;
    private final Map<String, String> parameters;
    private final List<String> addresses;
    private final Transport transport;
    private final MessageType messageType;

    private Message(Builder builder) {
        this.messageId = builder.messageId;
        this.subject = builder.subject;
        this.parameters = builder.parameters;
        this.addresses = builder.addresses;
        this.transport = builder.transport;
        this.messageType = builder.messageType;
    }

    public String getMessageId() {
        return messageId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @JsonAnyGetter
    public Map<String, String> getParameters() {
        return parameters;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public Transport getTransport() {
        return transport;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("messageId", messageId)
                .add("subject", subject)
                .add("parameters", parameters)
                .add("addresses", addresses)
                .add("transport", transport)
                .toString();
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private String messageId;
        private String subject;
        private MessageType messageType = MessageType.TEXT;

        private Map<String, String> parameters = new HashMap<>();
        private List<String> addresses;
        private Transport transport;

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setParameters(Map<String, String> parameters) {
            if (parameters != null) {
                this.parameters = parameters;
            }

            return this;
        }

        @JsonAnySetter
        public Builder setParameter(String param, String value) {
            this.parameters.put(param, value);
            return this;
        }

        public Builder setAddresses(List<String> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder setTransport(Transport transport) {
            this.transport = transport;
            return this;
        }

        public Builder setMessageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}
