package com.main.sistema_moedas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int saldo;

    public String consultarExtrato() {
        return null;
    }

    public void adicionar(int qtd){
        this.saldo += qtd;
    }

    public boolean retirar(int qtd){
        if(this.saldo < qtd) return false;
        this.saldo -= qtd;
        return true;
    }

    public int getSaldo() {
        return this.saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
