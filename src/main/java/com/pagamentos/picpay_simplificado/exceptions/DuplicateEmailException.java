package com.pagamentos.picpay_simplificado.exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("A user is already registered with email: " + email);
    }
}
