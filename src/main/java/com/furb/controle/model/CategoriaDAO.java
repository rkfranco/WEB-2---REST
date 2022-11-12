package com.furb.controle.model;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
public class CategoriaDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long CATEGORIA_ID;

    @Column
    private String nome;

    @Column
    private String descricao;

    public long getCATEGORIA_ID() {
        return CATEGORIA_ID;
    }

    public void setCATEGORIA_ID(long CATEGORIA_ID) {
        this.CATEGORIA_ID = CATEGORIA_ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}