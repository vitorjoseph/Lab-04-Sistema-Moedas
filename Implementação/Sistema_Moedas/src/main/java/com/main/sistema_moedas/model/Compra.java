package com.main.sistema_moedas.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.main.sistema_moedas.model.usuario.Aluno;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int valor;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Vantagem vantagem;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Aluno aluno;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime data;


    public Vantagem getVantagem() {
        return vantagem;
    }

    public void setVantagem(Vantagem vantagem) {
        this.vantagem = vantagem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public int getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }



    public Compra(int valor, Aluno aluno, Vantagem vantagem, LocalDateTime data) {
        this.valor = valor;
        this.vantagem = vantagem;
        this.aluno = aluno;
        this.data = data;
    }

    public Compra() {
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

}
