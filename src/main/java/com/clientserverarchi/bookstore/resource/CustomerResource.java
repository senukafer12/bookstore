package com.clientserverarchi.bookstore.resource;

import com.clientserverarchi.bookstore.exception.CustomerNotFoundException;
import com.clientserverarchi.bookstore.exception.InvalidInputException;
import com.clientserverarchi.bookstore.models.Customer;
import com.clientserverarchi.bookstore.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private static final Map<Long, Customer> customers = DataStore.getCustomers();

    @POST
    public Response createCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }
        if (customer.getEmail() == null || !customer.getEmail().contains("@")) {
            throw new InvalidInputException("Invalid email format");
        }
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new InvalidInputException("Password cannot be empty");
        }

        Customer createdCustomer = DataStore.addCustomer(customer);
        return Response.status(Response.Status.CREATED)
                .entity(createdCustomer)
                .build();
    }

    @GET
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") Long id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }

    @PUT
    @Path("/{id}")
    public Customer updateCustomer(@PathParam("id") Long id, Customer customer) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }
        if (customer.getEmail() == null || !customer.getEmail().contains("@")) {
            throw new InvalidInputException("Invalid email format");
        }

        customer.setId(id);
        customers.put(id, customer);
        return customer;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        Customer customer = customers.remove(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return Response.noContent().build();
    }
}
