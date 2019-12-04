package com.itzap.api.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@JsonDeserialize(builder = ResponseStatus.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseStatus {
    private final String address;
    private final MessageStatus status;

    private ResponseStatus(Builder builder) {
        this.address = builder.address;
        this.status = builder.status;
    }

    public String getAddress() {
        return address;
    }

    public MessageStatus getStatus() {
        return status;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private String address;
        private MessageStatus status;

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setStatus(MessageStatus status) {
            this.status = status;
            return this;
        }

        public ResponseStatus build() {
            return new ResponseStatus(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
