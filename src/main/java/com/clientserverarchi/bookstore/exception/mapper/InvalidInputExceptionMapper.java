package com.clientserverarchi.bookstore.exception.mapper;

import com.clientserverarchi.bookstore.exception.InvalidInputException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {
    @Override
    public Response toResponse(InvalidInputException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode(),
                "https://api.bookstore.com/errors/invalid-input"
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}