package com.clientserverarchi.bookstore.exception.mapper;

import com.clientserverarchi.bookstore.exception.OutOfStockException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
    @Override
    public Response toResponse(OutOfStockException exception) {
        Map<String, Object> responseEntity = new HashMap<>();
        responseEntity.put("message", exception.getMessage());
        responseEntity.put("status", Response.Status.CONFLICT.getStatusCode());
        responseEntity.put("documentation", "https://api.bookstore.com/errors/out-of-stock");

        if (exception.getAvailableStock() != null) {
            responseEntity.put("availableStock", exception.getAvailableStock());
        }

        return Response.status(Response.Status.CONFLICT)
                .entity(responseEntity)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
