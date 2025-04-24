package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.models.Book;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;

@Path("/authors")
public class AuthorResource {

    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") long authorId) {
        List<Book> books = new ArrayList<Book>();
        return books;
    }
}
