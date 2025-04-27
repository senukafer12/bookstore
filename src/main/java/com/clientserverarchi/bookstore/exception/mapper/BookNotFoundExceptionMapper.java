package com.clientserverarchi.bookstore.exception.mapper;

import com.clientserverarchi.bookstore.exception.BookNotFoundException;
import org.glassfish.jersey.internal.Errors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    @Override
    public Response toResponse(BookNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                Response.Status.NOT_FOUND.getStatusCode(),
                "https://api.bookstore.com/errors/book-not-found"
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
