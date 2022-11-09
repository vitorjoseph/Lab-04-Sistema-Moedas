package com.main.sistema_moedas.model.usuario;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nameRole;
    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

    public List<Usuario> getUsers() {
        return this.usuarios;
    }

    public void setUsers(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNameRole() {
        return this.nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String getAuthority() {
        return this.nameRole;
    }

    public Role () {

    }
    public Role(String nameRole) {
        this.nameRole = nameRole;
    }

}
