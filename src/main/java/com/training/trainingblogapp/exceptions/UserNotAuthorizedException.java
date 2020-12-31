package com.training.trainingblogapp.exceptions;

public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException() {
        super();
    }

    public UserNotAuthorizedException(String message) {
        super(message);
    }

    public UserNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
