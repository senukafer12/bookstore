package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.models.Order;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/customers/{customerId}/orders")
public class OrderResource {

    @POST
    public Order createOrder(@PathParam("customerId") Long customerId, Order order) {
        return order;
    }

    @GET
    public List<Order> getCustomerOrders(@PathParam("customerId") Long customerId) {
        return null;
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("customerId") Long customerId ,@PathParam("orderId") Long orderId) {
        return null;
    }
}
