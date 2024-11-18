package com.pagamentos.picpay_simplificado.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pagamentos.picpay_simplificado.models.DTO.requests.AppUserDTORequest;
import com.pagamentos.picpay_simplificado.models.DTO.responses.AppUserDTOResponse;
import com.pagamentos.picpay_simplificado.services.AppUserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app-users")
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<AppUserDTOResponse> createAppUser(@RequestBody AppUserDTORequest appUserDTORequest) {
        AppUserDTOResponse createdAppUserDTOResponse = appUserService.createAppUser(appUserDTORequest);
        return new ResponseEntity<>(createdAppUserDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<AppUserDTOResponse> getAppUser(@PathVariable String identifier) {
        AppUserDTOResponse appUserDTOResponse = appUserService.getAppUser(identifier);
        return ResponseEntity.ok(appUserDTOResponse);
    }

    @GetMapping("/")
    public ResponseEntity<List<AppUserDTOResponse>> getAllAppUsers() {
        List<AppUserDTOResponse> appUserDTOResponse = appUserService.getAllAppUsers();
        return ResponseEntity.ok(appUserDTOResponse);
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<AppUserDTOResponse> updateAppUser(@PathVariable String identifier,
            @RequestBody AppUserDTORequest appUserDTORequest) {
        AppUserDTOResponse updatedUser = appUserService.updateAppUser(identifier, appUserDTORequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> deleteAppUser(@PathVariable String identifier) {
        appUserService.deleteAppUser(identifier);
        return ResponseEntity.noContent().build();
    }

}
