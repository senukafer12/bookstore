package com.clientserverarchi.bookstore.exception;

public class CustomerNotFoundException extends RuntimeException {
    private final Long customerId;

    public CustomerNotFoundException(Long customerId) {
        super("Customer with id " + customerId + " not found");
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
