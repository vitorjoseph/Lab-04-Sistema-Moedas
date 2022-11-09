package com.main.sistema_moedas.model.usuario;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.main.sistema_moedas.model.Conta;
import com.main.sistema_moedas.model.Instituicao;

@Entity
public class Professor extends Usuario {

    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String cpf;
    private String departamento;
    @ManyToOne(targetEntity = Instituicao.class)
    private Instituicao instituicao;
    @OneToOne(cascade = CascadeType.ALL)
    private Conta conta;


    public Conta getConta() {
        return this.conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
//		instituicao.addProfessor(this);
    }

    public String toString(){
        return getNome();
    }

}
