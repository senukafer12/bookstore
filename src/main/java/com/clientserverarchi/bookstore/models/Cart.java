package com.clientserverarchi.bookstore.models;

import java.util.Map;

public class Cart {
    private Long customerId;
    private Map<Long, Integer> items; // bookId to quantity

    public Cart() {}

    public Cart(Long customerId, Map<Long, Integer> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}
