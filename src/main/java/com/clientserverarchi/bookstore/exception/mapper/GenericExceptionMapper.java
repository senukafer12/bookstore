package com.clientserverarchi.bookstore.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//this mapper class is for unhandled exceptions
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                "An unexpected error occurred: " + exception.getMessage(),
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "https://api.bookstore.com/errors/internal-server-error"
        );

        // Log the full exception
        exception.printStackTrace();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}