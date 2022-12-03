package com.furb.controle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class CategoriaDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private Set<ProdutoDAO> produto;

    @Column
    private String nome;

    @Column
    private String descricao;

    public Integer getCATEGORIA_ID() {
        return id;
    }

    public void setCATEGORIA_ID(int id) {
        this.id = id;
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
