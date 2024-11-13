package com.pagamentos.picpay_simplificado.services;

import org.springframework.stereotype.Service;

import com.pagamentos.picpay_simplificado.exceptions.*;
import com.pagamentos.picpay_simplificado.mappers.AppUserMappers;
import com.pagamentos.picpay_simplificado.models.AppUser;
import com.pagamentos.picpay_simplificado.models.DTO.requests.AppUserDTORequest;
import com.pagamentos.picpay_simplificado.models.DTO.responses.AppUserDTOResponse;
import com.pagamentos.picpay_simplificado.repositories.AppUserRepository;
import com.pagamentos.picpay_simplificado.validations.IdentifierValidator;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserDTOResponse createAppUser(AppUserDTORequest appUserDTORequest) {
        validateIdentifierLength(appUserDTORequest.getIdentifier());

        // Mapeia de AppUserDTORequest para AppUser e define o tipo de usuário
        AppUser appUser = defineAppUserType(AppUserMappers.toAppUser(appUserDTORequest));

        // Validações
        // use orElseThrow quando você espera que o valor exista e deseja lançar uma
        // exceção se ele não estiver presente.
        // use ifPresent quando você quer executar uma ação (como lançar uma exceção)
        // apenas se o valor estiver presente.
        appUserRepository.findByIdentifier(appUser.getIdentifier())
                .ifPresent(id -> {
                    throw new DuplicateIdentifierException(appUser.getIdentifier());
                });

        appUserRepository.findByEmail(appUser.getEmail())
                .ifPresent(id -> {
                    throw new DuplicateEmailException(appUser.getEmail());
                });

        // Mapeia de AppUser para AppUserDTOResponse e salva no banco
        return AppUserMappers.toAppUserDTOResponse(appUserRepository.save(appUser));
    }

    private void validateIdentifierLength(String identifier) {
        int length = identifier.length();
        if (length != 11 && length != 14) {
            throw new InvalidIdentifierException(identifier);
        }
    }

    private AppUser defineAppUserType(AppUser appUser) {
        if (IdentifierValidator.isCommonUser(appUser.getIdentifier())) {
            appUser.setType("CommonUser");
        } else if (IdentifierValidator.isCompanyUser(appUser.getIdentifier())) {
            appUser.setType("MerchantUser");
        }
        return appUser;
    }

    public AppUserDTOResponse getAppUser(String identifier) {
        AppUser appUser = appUserRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UserNotFoundException(identifier));
        return AppUserMappers.toAppUserDTOResponse(appUser);
    }

    public AppUserDTOResponse updateAppUser(String identifier, AppUserDTORequest appUserDTORequest) {
        AppUser appUser = appUserRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UserNotFoundException(identifier));

        appUserRepository.findByIdentifier(appUser.getIdentifier())
                .ifPresentOrElse(
                        id -> {
                            throw new DuplicateIdentifierException(appUser.getIdentifier());
                        },
                        () -> appUser.setEmail(appUserDTORequest.getEmail()));
        appUser.setFullName(appUserDTORequest.getFullName());
        appUser.setPassword(appUserDTORequest.getPassword());

        return AppUserMappers.toAppUserDTOResponse(appUserRepository.save(appUser));
    }

    public void deleteAppUser(String identifier) {
        AppUser appUser = appUserRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UserNotFoundException(identifier));
        appUserRepository.delete(appUser);
    }

}
