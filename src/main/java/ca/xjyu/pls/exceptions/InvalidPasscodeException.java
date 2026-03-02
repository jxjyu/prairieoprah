package ca.xjyu.pls.exceptions;

public class InvalidPasscodeException extends RuntimeException {
    public InvalidPasscodeException(String message) {
        super(message);
    }
}
