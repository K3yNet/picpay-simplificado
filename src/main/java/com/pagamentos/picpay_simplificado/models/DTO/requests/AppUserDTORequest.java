package com.pagamentos.picpay_simplificado.models.DTO.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserDTORequest {
    private String fullName;
    private String identifier;
    private String email;
    private String password;
}
