package com.pagamentos.picpay_simplificado.models;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "CPF/CNPJ is required")
    @Pattern(regexp = "^\\d{11}$|^\\d{14}$", message = "CPF must have 11 digits and CNPJ must have 14 digits")
    private String identifier;

    @NotBlank
    private String type;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal walletBalance = BigDecimal.ZERO;

}
