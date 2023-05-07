package edu.pucp.sisdvac.controller.exception;

public class UserAlreadyAddedException extends RuntimeException{
    public UserAlreadyAddedException(String message) {
        super(message);
    }
}
