package com.pagamentos.picpay_simplificado.exceptions;

public class NotificationFailedException extends RuntimeException {
    public NotificationFailedException(String message) {
        super(message);
    }
}
