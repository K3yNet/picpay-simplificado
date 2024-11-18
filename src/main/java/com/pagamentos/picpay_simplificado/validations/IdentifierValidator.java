package com.pagamentos.picpay_simplificado.validations;

public final class IdentifierValidator {
    public static boolean isCommonUser(String identifier) {
        return identifier != null && identifier.length() == 11;
    }

    public static boolean isMerchantUser(String identifier) {
        return identifier != null && identifier.length() == 14;
    }
}
