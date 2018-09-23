package com.atsi.message.rest;

import com.atsi.api.message.model.Message;
import com.atsi.core.base.DARTPreconditions;
import com.atsi.exceptions.ATSIEmailError;
import com.atsi.http.model.DARTErrorReturn;
import com.atsi.message.services.EMailService;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by avinokurov on 2/7/2017.
 */

@Api(value = "ATSI Message Service", produces = "application/json")
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
            @ApiResponse(code = 404, message = "No databases found", response = DARTErrorReturn.class),
            @ApiResponse(code = 500, message = "Internal Server error", response = DARTErrorReturn.class)
    })
    public Response send(@ApiParam("Message to send")
                         Message message) {
        DARTPreconditions.checkProvided(message, ATSIEmailError.EMAIL_ERROR);

        return Response
                .status(Response.Status.OK)
                .entity(mailService.send(message)
                        .toBlocking().single())
                .build();
    }
}
