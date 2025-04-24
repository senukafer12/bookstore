package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.models.Cart;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
public class CartResource {

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") Long customerId, CartItem item) {

    }

    @GET
    public Cart getCart(@PathParam("customerId") Long customerId) {

    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId) {

    }

    @PUT
    @Path("/items/{bookId}")
    public Response removeItemFromCart(@PathParam("customerId") Long customerId, @PathParam("bookId") Long bookId) {

    }
}
