package com.pagamentos.picpay_simplificado.validations;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        return isCPFValid(cpf);
    }

    private boolean isCPFValid(String cpf) {
        int[] multiplicador1 = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        int[] multiplicador2 = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        String tempCpf;
        int digito1, digito2;
        int soma, resto;

        if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }

        tempCpf = cpf.substring(0, 9);
        soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (tempCpf.charAt(i) - '0') * multiplicador1[i];
        }
        resto = 11 - (soma % 11);
        digito1 = (resto == 10 || resto == 11) ? 0 : resto;

        tempCpf += digito1;
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (tempCpf.charAt(i) - '0') * multiplicador2[i];
        }
        resto = 11 - (soma % 11);
        digito2 = (resto == 10 || resto == 11) ? 0 : resto;

        return cpf.equals(tempCpf + digito2);
    }
}
