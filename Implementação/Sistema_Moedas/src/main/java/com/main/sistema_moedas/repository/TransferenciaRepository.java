package com.main.sistema_moedas.repository;

import com.main.sistema_moedas.model.Conta;
import com.main.sistema_moedas.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    public List<Transferencia> findByContaOrigem(Conta conta);
    public List<Transferencia> findByContaDestino(Conta conta);
}