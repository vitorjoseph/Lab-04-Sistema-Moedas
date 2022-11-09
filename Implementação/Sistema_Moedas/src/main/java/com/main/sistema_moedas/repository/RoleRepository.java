package com.main.sistema_moedas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.main.sistema_moedas.model.usuario.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByNameRole(String nome);

}
