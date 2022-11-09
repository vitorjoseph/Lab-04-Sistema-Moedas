package com.main.sistema_moedas.model.usuario;

import javax.persistence.Entity;

@Entity
public class Empresa extends Usuario {

    private static final long serialVersionUID = 1L;

    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}