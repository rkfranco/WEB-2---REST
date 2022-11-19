package com.furb.controle.model;

import javax.persistence.*;

@Entity
@Table(name = "marca")
public class MarcaDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MARCA_ID;

    @Column
    private String razaoSocial;

    @Column
    private String nome;

    @Column
    private String cnpj;

    public int getMARCA_ID() {
        return MARCA_ID;
    }

    public void setMARCA_ID(int MARCA_ID) {
        this.MARCA_ID = MARCA_ID;
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

