package com.pagamentos.picpay_simplificado.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagamentos.picpay_simplificado.models.Transfer;
import com.pagamentos.picpay_simplificado.services.TransferService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private TransferService transferService;

    @PostMapping
    public ResponseEntity<String> transfer(@RequestBody Transfer transferRequest) {
        transferService.transfer(
                transferRequest.getPayerIdentifier(),
                transferRequest.getPayeeIdentifier(),
                transferRequest.getAmount());
        return ResponseEntity.ok("Transfer completed successfully");
    }
}
