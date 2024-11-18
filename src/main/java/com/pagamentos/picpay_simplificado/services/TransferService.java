package com.pagamentos.picpay_simplificado.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.pagamentos.picpay_simplificado.client.AuthorizationClient;
import com.pagamentos.picpay_simplificado.client.DTO.AuthorizationDTO;
import com.pagamentos.picpay_simplificado.exceptions.*;
import com.pagamentos.picpay_simplificado.models.AppUser;
import com.pagamentos.picpay_simplificado.models.Transfer;
import com.pagamentos.picpay_simplificado.repositories.AppUserRepository;
import com.pagamentos.picpay_simplificado.repositories.TransferRepository;
import com.pagamentos.picpay_simplificado.validations.IdentifierValidator;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AppUserRepository appUserRepository;
    private final AuthorizationClient authorizationClient;

    @Transactional(rollbackOn = Exception.class)
    public void transfer(String payerIdentifier, String peyeeIdentifier, BigDecimal amount) {
        // Lojistas só recebem transferências, não enviam dinheiro para ninguém
        if (IdentifierValidator.isMerchantUser(payerIdentifier)) {
            throw new MerchantCannotTransferException();
        }

        // Buscar usuários remetente
        AppUser payer = appUserRepository.findByIdentifier(payerIdentifier)
                .orElseThrow(() -> new UserNotFoundException(payerIdentifier));

        // Validando o saldo antes da proxima consulta no banco
        // Validar se o usuário tem saldo antes da transferência
        if (payer.getWalletBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        // Buscar usuário destinatário
        AppUser payee = appUserRepository.findByIdentifier(peyeeIdentifier)
                .orElseThrow(() -> new UserNotFoundException(peyeeIdentifier));

        transferRepository.save(
                Transfer.builder()
                        .payerIdentifier(payerIdentifier)
                        .payeeIdentifier(peyeeIdentifier)
                        .amount(amount)
                        .build());
        try {
            authorizationClient.authorize();
            // Atualizar saldos
            payer.setWalletBalance(payer.getWalletBalance().subtract(amount));
            payee.setWalletBalance(payee.getWalletBalance().add(amount));

            // Salvar alterações no banco
            appUserRepository.save(payer);
            appUserRepository.save(payee);

        } catch (FeignException e) {
            throw new AuthorizationFailedException();
        }
    }
}
