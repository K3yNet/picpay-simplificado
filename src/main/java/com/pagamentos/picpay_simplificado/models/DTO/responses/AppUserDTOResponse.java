package com.pagamentos.picpay_simplificado.models.DTO.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTOResponse {
    private String fullName;
    private String identifier;
    private String type;
    private String email;
}
