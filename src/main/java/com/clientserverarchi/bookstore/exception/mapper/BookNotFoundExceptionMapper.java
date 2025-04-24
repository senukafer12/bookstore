package com.clientserverarchi.bookstore.exception.mapper;

import com.clientserverarchi.bookstore.exception.BookNotFoundException;
import org.glassfish.jersey.internal.Errors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    @Override
    public Response toResponse(BookNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "http://example.com/errors/not-found");
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
    }
}
