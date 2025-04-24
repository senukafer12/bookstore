package com.clientserverarchi.bookstore.exception.mapper;

import java.util.Date;

public class ErrorMessage {
    private String message;
    private int status;
    private String documentation;
    private Date timestamp;

    public ErrorMessage() {
        this.timestamp = new Date();
    }

    public ErrorMessage(String message, int status, String documentation) {
        this();
        this.message = message;
        this.status = status;
        this.documentation = documentation;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

