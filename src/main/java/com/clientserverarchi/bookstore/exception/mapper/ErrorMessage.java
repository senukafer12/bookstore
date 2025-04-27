package com.clientserverarchi.bookstore.exception.mapper;

import java.time.Instant;

public class ErrorMessage {
    private String message;
    private int status;
    private String documentation;
    private String timestamp;

    public ErrorMessage(String message, int status, String documentation) {
        this.message = message;
        this.status = status;
        this.documentation = documentation;
        this.timestamp = Instant.now().toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

