package com.pagamentos.picpay_simplificado.client.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDTO {
    private String status;
    private AuthorizationDataDTO data;

    @Data
    public class AuthorizationDataDTO {
        @JsonProperty("authorization")
        private boolean authorized;
    }
}
