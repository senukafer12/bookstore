package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.exception.CartNotFoundException;
import com.clientserverarchi.bookstore.exception.CustomerNotFoundException;
import com.clientserverarchi.bookstore.exception.OrderNotFoundException;
import com.clientserverarchi.bookstore.exception.OutOfStockException;
import com.clientserverarchi.bookstore.models.Book;
import com.clientserverarchi.bookstore.models.Cart;
import com.clientserverarchi.bookstore.models.Order;
import com.clientserverarchi.bookstore.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @POST
    public Response createOrder(@PathParam("customerId") Long customerId) {
        if (!DataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        Cart cart = DataStore.getCarts().get(customerId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new CartNotFoundException("Cart is empty");
        }

        // Check stock availability
        Map<Long, Integer> unavailableItems = new HashMap<>();
        cart.getItems().forEach((bookId, quantity) -> {
            Book book = DataStore.getBooks().get(bookId);
            if (book.getStockQuantity() < quantity) {
                unavailableItems.put(bookId, book.getStockQuantity());
            }
        });

        if (!unavailableItems.isEmpty()) {
            throw new OutOfStockException("Some items are out of stock", unavailableItems);
        }

        // Create order
        Order order = new Order();
        order.setId(DataStore.getNextOrderId());
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(new HashMap<>(cart.getItems()));

        // Calculate total
        double total = cart.getItems().entrySet().stream()
                .mapToDouble(e -> DataStore.getBooks().get(e.getKey()).getPrice() * e.getValue())
                .sum();
        order.setTotalAmount(total);

        // Update stock and save order
        cart.getItems().forEach((bookId, quantity) -> {
            Book book = DataStore.getBooks().get(bookId);
            book.setStockQuantity(book.getStockQuantity() - quantity);
        });

        DataStore.getOrders().put(order.getId(), order);
        DataStore.getCarts().remove(customerId);

        return Response.status(Response.Status.CREATED)
                .entity(order)
                .build();
    }

    @GET
    public List<Order> getCustomerOrders(@PathParam("customerId") Long customerId) {
        if (!DataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        return DataStore.getOrders().values().stream()
                .filter(order -> customerId.equals(order.getCustomerId()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("customerId") Long customerId,
                          @PathParam("orderId") Long orderId) {
        if (!DataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        Order order = DataStore.getOrders().get(orderId);
        if (order == null || !customerId.equals(order.getCustomerId())) {
            throw new OrderNotFoundException("Order not found");
        }
        return order;
    }
}
