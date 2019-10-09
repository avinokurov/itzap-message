package com.itzap.message.rest.mappers;

import com.itzap.api.message.model.ErrorResponse;
import com.itzap.common.exception.IZapException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IZapExceptionMapper implements ExceptionMapper<IZapException> {
    @Override
    public Response toResponse(IZapException e) {
        ErrorResponse response = MapperUtils.toReturn(e);

        return Response.status(response.getStatus())
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
