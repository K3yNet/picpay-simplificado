package com.pagamentos.picpay_simplificado.exceptions;

public class MerchantCannotTransferException extends RuntimeException {
    public MerchantCannotTransferException() {
        super("Merchant cannot transfer.");
    }
}
