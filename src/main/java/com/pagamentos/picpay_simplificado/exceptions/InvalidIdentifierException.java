package com.pagamentos.picpay_simplificado.exceptions;

public class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException(String identifier) {
        super("Invalid identifier length. Expected 11 characters for CPF or 14 characters for CNPJ.");
    }
}
