package com.main.sistema_moedas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.sistema_moedas.model.Compra;
import com.main.sistema_moedas.model.usuario.Aluno;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByAluno(Aluno a);

}
