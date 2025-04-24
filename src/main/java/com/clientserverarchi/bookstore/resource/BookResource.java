package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.models.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    @POST
    public Response createBook(Book book) {
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        return books;
    }

    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") Long id) {
        Book book = new Book();
        return book;
    }

    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book) {
        book.setId(id);
        return book;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        Book book = new Book();
        book.setId(id);
        return Response.status(Response.Status.OK).entity(book).build();
    }

}
