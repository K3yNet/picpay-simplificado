package com.pagamentos.picpay_simplificado.mappers;

import com.pagamentos.picpay_simplificado.models.AppUser;
import com.pagamentos.picpay_simplificado.models.DTO.requests.AppUserDTORequest;
import com.pagamentos.picpay_simplificado.models.DTO.responses.AppUserDTOResponse;

public final class AppUserMappers {
    public static AppUser toAppUser(AppUserDTORequest appUserDTORequest) {
        AppUser appUser = new AppUser();
        appUser.setFullName(appUserDTORequest.getFullName());
        appUser.setIdentifier(appUserDTORequest.getIdentifier());
        appUser.setEmail(appUserDTORequest.getEmail());
        appUser.setPassword(appUserDTORequest.getPassword());
        return appUser;
    }

    public static AppUserDTOResponse toAppUserDTOResponse(AppUser appUser) {
        AppUserDTOResponse appUserDTOResponse = new AppUserDTOResponse();
        appUserDTOResponse.setFullName(appUser.getFullName());
        appUserDTOResponse.setIdentifier(appUser.getIdentifier());
        appUserDTOResponse.setType(appUser.getType());
        appUserDTOResponse.setEmail(appUser.getEmail());
        return appUserDTOResponse;
    }
}
