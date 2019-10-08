package com.itzap.message.exceptions;

import com.itzap.common.ErrorCode;
import org.apache.http.HttpStatus;

public enum ITZapEmailError implements ErrorCode {
    EMAIL_ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR,
            "ITZap.EMAIL.ERROR", "ITZap Email service error"),
    MESSAGE_MISSING_ERROR(HttpStatus.SC_BAD_REQUEST,
            "ITZap.EMAIL.ERROR", "Message needs to be provided");

    private final int status;
    private final String code;
    private final String message;

    ITZapEmailError(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
