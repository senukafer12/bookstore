package com.clientserverarchi.bookstore.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(Long Id) {
        super("Book with id " + Id + " is out of stock");
    }
}
