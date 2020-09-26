package org.techtalent.exceptions;

public class IsNotYourTurnException extends RuntimeException {
    public IsNotYourTurnException(String message) {
        super(message);
    }
}
