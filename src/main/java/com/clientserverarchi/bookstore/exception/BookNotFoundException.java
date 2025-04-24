package com.clientserverarchi.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
    private final Long bookId;

    public BookNotFoundException(Long bookId) {
        super("Book with id " + bookId + " not found");
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }
}
