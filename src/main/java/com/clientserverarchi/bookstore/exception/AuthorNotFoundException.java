package com.clientserverarchi.bookstore.exception;

public class AuthorNotFoundException extends RuntimeException{
    private final Long authorId;

    public AuthorNotFoundException(Long authorId) {
        super("Author with id " + authorId + " not found");
        this.authorId = authorId;
    }

    public Long getAuthorId() {
        return authorId;
    }
}
