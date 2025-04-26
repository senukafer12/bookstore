package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.exception.AuthorNotFoundException;
import com.clientserverarchi.bookstore.exception.InvalidInputException;
import com.clientserverarchi.bookstore.models.Author;
import com.clientserverarchi.bookstore.models.Book;
import com.clientserverarchi.bookstore.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/authors")
public class AuthorResource {
    private static final Map<Long, Author> authors = DataStore.getAuthors();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty");
        }

        Author createdAuthor = DataStore.addAuthor(author);
        return Response.status(Response.Status.CREATED)
                .entity(createdAuthor)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() {
        return new ArrayList<>(authors.values());
    }

    @GET
    @Path("/{id}")
    public Author getAuthor(@PathParam("id") Long id) {
        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return author;
    }

    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") Long id, Author author) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty");
        }

        author.setId(id);
        authors.put(id, author);
        return author;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        Author author = authors.remove(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") Long authorId) {
        if (!authors.containsKey(authorId)) {
            throw new AuthorNotFoundException(authorId);
        }

        return DataStore.getBooks().values().stream()
                .filter(book -> authorId.equals(book.getAuthorId()))
                .collect(Collectors.toList());
    }
}
