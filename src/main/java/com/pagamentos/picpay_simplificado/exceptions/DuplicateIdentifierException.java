package com.pagamentos.picpay_simplificado.exceptions;

public class DuplicateIdentifierException extends RuntimeException {
    public DuplicateIdentifierException(String identifier) {
        super("A user is already registered with identifier: " + identifier);
    }
}
