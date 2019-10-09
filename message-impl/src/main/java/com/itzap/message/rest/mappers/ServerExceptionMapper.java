package com.itzap.message.rest.mappers;

import com.itzap.api.message.model.ErrorResponse;
import com.itzap.message.exceptions.ITZapEmailError;
import org.apache.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        ErrorResponse response = ErrorResponse.builder()
                .setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .setErrorMessage(e.getMessage())
                .setErrorCode(ITZapEmailError.EMAIL_ERROR)
                .build();

        return Response.status(response.getStatus())
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
