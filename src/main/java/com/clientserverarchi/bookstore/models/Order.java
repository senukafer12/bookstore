package com.clientserverarchi.bookstore.models;

import java.time.LocalDateTime;
import java.util.Map;

public class Order {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private Map<Long, Integer> items; // bookId to quantity
    private double totalAmount;

    public Order() {}

    public Order(Long id, Long customerId, LocalDateTime orderDate, Map<Long, Integer> items, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
