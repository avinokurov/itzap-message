package com.itzap.message.rest.mappers;

import com.itzap.api.message.model.ErrorResponse;
import com.itzap.common.exception.IZapException;
import com.itzap.message.exceptions.ITZapEmailError;
import org.apache.http.HttpStatus;

public final class MapperUtils {
    private MapperUtils() {}

    public static ErrorResponse toReturn(int status,
                                         Throwable exception) {

        return ErrorResponse.builder()
                .setStatus(status)
                .setErrorMessage(exception.getMessage())
                .setErrorCode(ITZapEmailError.EMAIL_ERROR)
                .build();
    }

    public static ErrorResponse toReturn(IZapException itzapException) {
        return ErrorResponse.builder()
                .setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .setErrorMessage(itzapException.getMessage())
                .setErrorCode(itzapException.getErrorCode())
                .build();
    }
}
