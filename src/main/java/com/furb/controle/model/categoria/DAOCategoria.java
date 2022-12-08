package com.furb.controle.model.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.furb.controle.model.produto.DAOProduto;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class DAOCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private Set<DAOProduto> produto;

    @Column
    private String nome;

    @Column
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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
