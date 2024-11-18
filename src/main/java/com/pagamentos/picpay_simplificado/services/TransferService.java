package com.pagamentos.picpay_simplificado.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.pagamentos.picpay_simplificado.exceptions.InsufficientBalanceException;
import com.pagamentos.picpay_simplificado.exceptions.UserNotFoundException;
import com.pagamentos.picpay_simplificado.models.AppUser;
import com.pagamentos.picpay_simplificado.models.Transfer;
import com.pagamentos.picpay_simplificado.repositories.AppUserRepository;
import com.pagamentos.picpay_simplificado.repositories.TransferRepository;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AppUserRepository appUserRepository;

    @Transactional(rollbackOn = Exception.class)
    public void transfer(String payerIdentifier, String peyeeIdentifier, BigDecimal amount) {
        
        // Buscar usuários remetente e destinatário pelo identifier
        AppUser payer = appUserRepository.findByIdentifier(payerIdentifier)
                .orElseThrow(() -> new UserNotFoundException(payerIdentifier));

        AppUser payee = appUserRepository.findByIdentifier(peyeeIdentifier)
                .orElseThrow(() -> new UserNotFoundException(peyeeIdentifier));

        // Verificar saldo do remetente
        if (payer.getWalletBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        transferRepository.save(
                Transfer.builder()
                        .payerIdentifier(payerIdentifier)
                        .payeeIdentifier(peyeeIdentifier)
                        .amount(amount)
                        .build());

        // Atualizar saldos
        payer.setWalletBalance(payer.getWalletBalance().subtract(amount));
        payee.setWalletBalance(payee.getWalletBalance().add(amount));

        // Salvar alterações no banco
        appUserRepository.save(payer);
        appUserRepository.save(payee);
    }
}
