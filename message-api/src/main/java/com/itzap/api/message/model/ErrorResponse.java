package com.itzap.api.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.itzap.common.ErrorCode;
import com.itzap.common.ErrorDescriptor;
import com.itzap.common.exception.IZapException;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonDeserialize(builder = ErrorResponse.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements ErrorDescriptor {
    private final ErrorCode errorCode;
    private final String errorMessage;
    private final int status;

    public ErrorResponse(Builder builder) {
        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
        this.status = builder.status;
    }

    @Override
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public MessageStatus getMessageStatus() {
        return MessageStatus.ERROR;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public ErrorDescriptor checkDescriptor(String checkMessage) {
        return IZapException.descriptor(getErrorCode(), getErrorMessage());
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private ErrorCode errorCode;
        private String errorMessage;
        private int status;

        public Builder setErrorCode(ErrorCode errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
