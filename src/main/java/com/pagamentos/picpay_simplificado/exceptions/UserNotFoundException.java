package com.pagamentos.picpay_simplificado.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identifier) {
        super("User with identifier " + identifier + " not found.");
    }
}
