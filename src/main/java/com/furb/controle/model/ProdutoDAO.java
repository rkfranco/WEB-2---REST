package com.furb.controle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "produto")
public class ProdutoDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "produto_marca_id", nullable = false)
    private MarcaDAO marca;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "produto_categoria_id", nullable = false)
    private CategoriaDAO categoria;
    @Column
    private String nome;

    @Column
    private Double preco;

    @Column
    private String descricao;

    @Column
    private Integer qtdEstoque;

    public CategoriaDAO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDAO categoria) {
        this.categoria = categoria;
    }

    public MarcaDAO getMarca() {
        return marca;
    }

    public void setMarca(MarcaDAO marca) {
        this.marca = marca;
    }

    public int getId() {
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }
}
