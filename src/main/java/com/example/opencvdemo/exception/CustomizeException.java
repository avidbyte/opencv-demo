package com.example.opencvdemo.exception;

/**
 * @author aaron
 * @since 2020-10-10
 */
public class CustomizeException extends RuntimeException{

    private static final long serialVersionUID = 8824172854339309096L;

    private int status = 500;

    private String message = "unknown exception";

    public CustomizeException() {
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    public CustomizeException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public CustomizeException(int status, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }

    public CustomizeException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public CustomizeException(int status, Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.status = status;
    }

    public CustomizeException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
