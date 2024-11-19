package com.pagamentos.picpay_simplificado.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.pagamentos.picpay_simplificado.client.AuthorizationClient;
import com.pagamentos.picpay_simplificado.client.NotificationClient;
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
    private final NotificationClient notificationClient;

    @Transactional(rollbackOn = Exception.class)
    public void transfer(String payerIdentifier, String payeeIdentifier, BigDecimal amount) {
        // Validar se o pagador é um lojista
        if (IdentifierValidator.isMerchantUser(payerIdentifier)) {
            throw new MerchantCannotTransferException();
        }

        // Buscar e validar usuários
        AppUser payer = findUserByIdentifier(payerIdentifier);
        AppUser payee = findUserByIdentifier(payeeIdentifier);

        // Validar saldo
        validateBalance(payer, amount);

        // Autorizar transferência
        authorizeTransfer();

        // Persistir transferência
        saveTransfer(payerIdentifier, payeeIdentifier, amount);

        // Atualizar saldos
        updateBalances(payer, payee, amount);

        // Enviar notificações
        sendNotification();
    }

    private AppUser findUserByIdentifier(String identifier) {
        return appUserRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UserNotFoundException(identifier));
    }

    private void validateBalance(AppUser payer, BigDecimal amount) {
        if (payer.getWalletBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
    }

    private void authorizeTransfer() {
        try {
            AuthorizationDTO authorization = authorizationClient.authorize();
            if (!authorization.getData().isAuthorized()) {
                throw new AuthorizationFailedException("Authorization denied.");
            }
        } catch (FeignException.BadRequest e) {
            throw new AuthorizationFailedException("Bad request to authorization service.");
        } catch (FeignException e) {
            throw new AuthorizationFailedException("Unauthorized transaction.");
        }
    }

    private void saveTransfer(String payerIdentifier, String payeeIdentifier, BigDecimal amount) {
        transferRepository.save(
                Transfer.builder()
                        .payerIdentifier(payerIdentifier)
                        .payeeIdentifier(payeeIdentifier)
                        .amount(amount)
                        .build());
    }

    private void updateBalances(AppUser payer, AppUser payee, BigDecimal amount) {
        payer.setWalletBalance(payer.getWalletBalance().subtract(amount));
        payee.setWalletBalance(payee.getWalletBalance().add(amount));

        appUserRepository.save(payer);
        appUserRepository.save(payee);
    }

    private void sendNotification() {
        try {
            notificationClient.sendNotification();
        } catch (FeignException e) {
            throw new NotificationFailedException("Failed to send notification.");
        }
    }
}
