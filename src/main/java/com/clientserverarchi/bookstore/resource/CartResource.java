package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.exception.*;
import com.clientserverarchi.bookstore.models.Book;
import com.clientserverarchi.bookstore.models.Cart;
import com.clientserverarchi.bookstore.models.CartItem;
import com.clientserverarchi.bookstore.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") Long customerId, CartItem item) {
        if (!DataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        if (item.getBookId() == null || !DataStore.getBooks().containsKey(item.getBookId())) {
            throw new BookNotFoundException(item.getBookId());
        }
        if (item.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0");
        }

        Book book = DataStore.getBooks().get(item.getBookId());
        if (book.getStockQuantity() < item.getQuantity()) {
            throw new OutOfStockException("Not enough stock available");
        }

        Cart cart = DataStore.getOrCreateCart(customerId);
        cart.getItems().merge(item.getBookId(), item.getQuantity(), Integer::sum);

        return Response.status(Response.Status.CREATED)
                .entity(cart)
                .build();
    }

    @GET
    public Cart getCart(@PathParam("customerId") Long customerId) {
        if (!DataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        Cart cart = DataStore.getCarts().get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer " + customerId);
        }
        return cart;
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") Long customerId,
                                   @PathParam("bookId") Long bookId,
                                   int quantity) {
        if (!DataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        if (!DataStore.getBooks().containsKey(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0");
        }

        Book book = DataStore.getBooks().get(bookId);
        if (book.getStockQuantity() < quantity) {
            throw new OutOfStockException("Not enough stock available");
        }

        Cart cart = DataStore.getOrCreateCart(customerId);
        cart.getItems().put(bookId, quantity);

        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeItemFromCart(@PathParam("customerId") Long customerId,
                                       @PathParam("bookId") Long bookId) {
        if (!DataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        Cart cart = DataStore.getCarts().get(customerId);
        if (cart == null || !cart.getItems().containsKey(bookId)) {
            throw new CartNotFoundException("Item not found in cart");
        }

        cart.getItems().remove(bookId);
        return Response.noContent().build();
    }
}
