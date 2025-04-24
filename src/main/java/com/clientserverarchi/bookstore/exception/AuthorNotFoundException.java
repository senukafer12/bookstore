package com.clientserverarchi.bookstore.exception;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(Long Id) {
        super("Author with id " + Id + " not found");
    }
}
