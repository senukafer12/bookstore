package com.clientserverarchi.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long Id) {
        super("Book with id " + Id + " not found");
    }
}
