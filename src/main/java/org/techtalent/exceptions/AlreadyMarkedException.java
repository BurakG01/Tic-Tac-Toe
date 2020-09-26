package org.techtalent.exceptions;

public class AlreadyMarkedException extends RuntimeException {
    public AlreadyMarkedException(String message) {
        super(message);
    }
}
