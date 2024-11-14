package com.pagamentos.picpay_simplificado.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Insufficient balance.");
    }
}
