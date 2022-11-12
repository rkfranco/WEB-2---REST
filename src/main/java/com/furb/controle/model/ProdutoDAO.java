package com.furb.controle.model;

import javax.persistence.*;

@Entity
@Table(name = "produto")
public class ProdutoDAO {

    @EmbeddedId
    private ProdutosCadastradosPK produtosCadastradosPK;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nome;

    @Column
    private double preco;

    @Column
    private String descricao;

    @Column
    private int qtdEstoque;

    public ProdutosCadastradosPK getProdutosCadastradosPK() {
        return produtosCadastradosPK;
    }

    public void setProdutosCadastradosPK(ProdutosCadastradosPK produtosCadastradosPK) {
        this.produtosCadastradosPK = produtosCadastradosPK;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
