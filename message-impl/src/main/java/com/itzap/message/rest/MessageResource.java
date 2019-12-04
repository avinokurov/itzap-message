package com.itzap.message.rest;

import com.itzap.api.message.model.Message;
import com.itzap.api.message.model.ResponseStatus;
import com.itzap.common.utils.PreconditionUtils;
import com.itzap.message.exceptions.ITZapEmailError;
import com.itzap.message.services.EMailService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
@OpenAPIDefinition(info = @Info(title = "ITZap Message Service", version = "v1"))
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
    @Operation(summary = "Send message",
            responses = {
                    @ApiResponse(description = "The user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseStatus.class)))
            })
    public Response send(@RequestBody(description = "Send message", required = true,
            content = @Content(
                    schema = @Schema(implementation = Message.class)))
                                 Message message) {
        PreconditionUtils.checkNotNull(message, descriptor(ITZapEmailError.EMAIL_ERROR));

        return Response
                .status(Response.Status.OK)
                .entity(mailService.send(message)
                        .blockingFirst())
                .build();
    }
}
