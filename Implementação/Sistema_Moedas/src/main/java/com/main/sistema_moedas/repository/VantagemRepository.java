package com.main.sistema_moedas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.sistema_moedas.model.Vantagem;
import com.main.sistema_moedas.model.usuario.Empresa;

@Repository
public interface VantagemRepository extends JpaRepository<Vantagem, Long> {

    List<Vantagem> findByEmpresa(Empresa empresa);
}
