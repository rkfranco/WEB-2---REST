package com.furb.controle.model.marca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.furb.controle.model.produto.ProdutoDAO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "marca")
public class MarcaDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int marcaId;

    @JsonIgnore
    @OneToMany(mappedBy = "marca")
    private Set<ProdutoDAO> produto;

    @Column
    private String razaoSocial;

    @Column
    private String nome;

    @Column
    private String cnpj;

    public Set<ProdutoDAO> getProduto() {
        return produto;
    }

    public void setProduto(Set<ProdutoDAO> produto) {
        this.produto = produto;
    }

    public int getMARCA_ID() {
        return marcaId;
    }

    public void setMARCA_ID(int MARCA_ID) {
        this.marcaId = MARCA_ID;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}

