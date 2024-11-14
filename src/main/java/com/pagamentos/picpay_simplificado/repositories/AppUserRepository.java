package com.pagamentos.picpay_simplificado.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagamentos.picpay_simplificado.models.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByIdentifier(String identifier);

    Optional<AppUser> findByEmail(String email);
}