package com.training.trainingblogapp.exceptions;

public class NotUniqueException extends RuntimeException {

    public NotUniqueException() {
        super();
    }

    public NotUniqueException(String message) {
        super(message);
    }

    public NotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueException(Throwable cause) {
        super(cause);
    }
}
