package com.clientserverarchi.bookstore.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long Id) {
        super("Cart with id " + Id + " not found");
    }
}
