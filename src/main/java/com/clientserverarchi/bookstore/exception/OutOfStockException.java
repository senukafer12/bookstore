package com.clientserverarchi.bookstore.exception;

import java.util.Map;

public class OutOfStockException extends RuntimeException {
    private Map<Long, Integer> availableStock;

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Map<Long, Integer> availableStock) {
        super(message);
        this.availableStock = availableStock;
    }

    public Map<Long, Integer> getAvailableStock() {
        return availableStock;
    }
}
