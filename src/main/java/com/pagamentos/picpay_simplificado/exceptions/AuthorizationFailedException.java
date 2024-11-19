package com.pagamentos.picpay_simplificado.exceptions;

public class AuthorizationFailedException extends RuntimeException {
    public AuthorizationFailedException(String message) {
        super(message);
    }
}
