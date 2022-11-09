package com.main.sistema_moedas.model.usuario;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.main.sistema_moedas.model.Conta;
import com.main.sistema_moedas.model.Instituicao;

@Entity
public class Aluno extends Usuario {

    private static final long serialVersionUID = 1L;

    private String cpf;
    private String rg;
    @OneToOne(cascade = CascadeType.ALL)
    private Conta conta;
    @ManyToOne(targetEntity = Instituicao.class)
    private Instituicao instituicao;


    public Conta getConta() {
        return conta;
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
//		instituicao.addAluno(this);
    }

    public String toString(){
        return getNome();
    }

}