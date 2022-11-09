package com.main.sistema_moedas.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.main.sistema_moedas.model.usuario.Empresa;

@Entity
public class Vantagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String produto;
    private int valor;
    private String descricao;
    private String foto;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Empresa empresa;



    public Vantagem() {
    }
    public Vantagem(String produto,int valor,String descricao,Empresa empresa) {
        this.setProduto(produto);
        this.setValor(valor);
        this.setDescricao(descricao);
        this.setEmpresa(empresa);

    }
    public String getFoto() {
        return this.foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
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
    public Empresa getEmpresa(){
        return empresa;
    }
    public void setEmpresa(Empresa empresa){
        this.empresa=empresa;
    }

}
