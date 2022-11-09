package com.main.sistema_moedas.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int valor;
    private String descricao;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Conta contaOrigem;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Conta contaDestino;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime data;

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta origem) {
        this.contaOrigem = origem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta destino) {
        this.contaDestino = destino;
    }

    @Override
    public String toString() {
        return "Transacao [id=" + this.id + ", valor=" + this.valor + ", descricao=" + this.descricao + ", contaOrigem="
                + this.contaOrigem.getId() + ", contaDestino=" + this.contaDestino.getId() + ", data=" + this.data.toString() + "]";
    }

}
