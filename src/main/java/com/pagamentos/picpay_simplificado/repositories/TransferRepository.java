package com.pagamentos.picpay_simplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pagamentos.picpay_simplificado.models.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
