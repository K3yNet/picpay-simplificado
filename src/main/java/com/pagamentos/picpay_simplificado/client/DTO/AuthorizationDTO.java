package com.pagamentos.picpay_simplificado.client.DTO;

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
    private DataDTO data;
}
