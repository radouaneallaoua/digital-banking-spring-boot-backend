package net.allaoua.digitalbanking.exceptions;

public class BalanceInsufficientException extends RuntimeException {
    public BalanceInsufficientException(String message) {
        super(message);
    }

}
