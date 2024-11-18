package com.pagamentos.picpay_simplificado.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.pagamentos.picpay_simplificado.client.DTO.AuthorizationDTO;

@FeignClient(name = "authorizationClient", url = "https://util.devi.tools/api/v2")
public interface AuthorizationClient {
    @GetMapping("/authorize")
    AuthorizationDTO authorize();
}