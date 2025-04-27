package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.exception.AuthorNotFoundException;
import com.clientserverarchi.bookstore.exception.BookNotFoundException;
import com.clientserverarchi.bookstore.exception.InvalidInputException;
import com.clientserverarchi.bookstore.models.Book;
import com.clientserverarchi.bookstore.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private static final Map<Long, Book> books = DataStore.getBooks();

    @POST
    public Response createBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty");
        }
        if (book.getAuthorId() == null || !DataStore.getAuthors().containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException(book.getAuthorId());
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (book.getPublicationYear() > currentYear) {
            throw new InvalidInputException("Publication year cannot be greater than current year");
        }
        Book createdBook = DataStore.addBook(book);
        return Response.status(Response.Status.CREATED)
                .entity(createdBook)
                .build();
    }

    @GET
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException(id);
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty");
        }

        book.setId(id);
        books.put(id, book);
        return book;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        Book book = books.remove(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return Response.noContent().build();
    }
}
