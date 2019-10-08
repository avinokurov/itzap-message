package com.itzap.message.rest;

import com.itzap.api.message.model.Message;
import com.itzap.api.message.model.ResponseStatus;
import com.itzap.common.utils.PreconditionUtils;
import com.itzap.message.exceptions.ITZapEmailError;
import com.itzap.message.services.EMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.itzap.common.exception.IZapException.descriptor;

/**
 * Created by avinokurov on 2/7/2017.
 */

@Api(value = "ITZap Message Service", produces = "application/json")
@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
    private final EMailService mailService;

    @Inject
    public MessageResource(EMailService mailService) {
        this.mailService = mailService;
    }

    @POST
    @ApiOperation(value = "Send email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Message sent"),
            @ApiResponse(code = 404, message = "No databases found", response = ResponseStatus.class),
            @ApiResponse(code = 500, message = "Internal Server error", response = ResponseStatus.class)
    })
    public Response send(@ApiParam("Message to send")
                         Message message) {
        PreconditionUtils.checkNotNull(message, descriptor(ITZapEmailError.EMAIL_ERROR));

        return Response
                .status(Response.Status.OK)
                .entity(mailService.send(message)
                        .blockingFirst())
                .build();
    }
}
